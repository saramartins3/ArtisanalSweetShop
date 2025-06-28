package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control;

import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database.*;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.*;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DAOException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DBConnectionException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.OperationException;

import java.sql.Date;
import java.util.*;

public class AcquistoOnlineControl{

	private static final Map<Integer, List<DettagliOrdineEntity>> carrelliInAttesa = new HashMap<>();
    private static final Map<Integer, OrdineEntity> ordiniTemporanei = new HashMap<>();
    private static final Map<Integer, String> cartaCreditoTemporanea = new HashMap<>();
    private static int nextIdCarrello = 1;
    private static final int SOGLIA_CLIENTE_ABITUALE = 3;

    public static class ProdottoQuantita {
        public String codiceProdotto;
        public int quantita;
    }

    public static class InputOrdine {
        public String nomeUtente;
        public List<ProdottoQuantita> prodotti;
        public String codiceSconto;
        public String indirizzo;
        public String cartaCredito;
    }

    public static class OutputOrdine {
        public List<DettagliOrdineEntity> dettagli;
        public float totale;
        public String messaggio;
        public int idCarrello;

        public OutputOrdine(List<DettagliOrdineEntity> dettagli, float totale, String messaggio, int idCarrello) {
            this.dettagli = dettagli;
            this.totale = totale;
            this.messaggio = messaggio;
            this.idCarrello = idCarrello;
        }
    }

    public static class OutputPagamentoFallito {
        public String messaggio;

        public OutputPagamentoFallito(String messaggio) {
            this.messaggio = messaggio;
        }
    }

    public OutputOrdine AcquistoOnline(InputOrdine input) throws OperationException {
        try {
            ClienteRegistratoEntity cliente = ClienteRegistratoDAO.leggiCliente(input.nomeUtente);
            if (cliente == null) {
                throw new OperationException("Utente non trovato.");
            }

            float totale = 0f;
            List<DettagliOrdineEntity> dettagli = new ArrayList<>();

            for (ProdottoQuantita p : input.prodotti) {
                ProdottoEntity prod = ProdottoDAO.leggiProdotto(p.codiceProdotto);
                if (prod == null || prod.getQuantitaDisponibile() < p.quantita) {
                    throw new OperationException("Prodotto non disponibile: " + p.codiceProdotto);
                }
                totale += prod.getPrezzo() * p.quantita;
                dettagli.add(new DettagliOrdineEntity(0, p.codiceProdotto, p.quantita));
            }

            if (input.codiceSconto != null && !input.codiceSconto.trim().isEmpty()) {
                if (cliente.getNumeroOrdini() < SOGLIA_CLIENTE_ABITUALE) {
                    throw new OperationException("Sconto riservato ai clienti abituali.");
                }
                ScontoEntity sconto = ScontoDAO.leggiScontoValido(input.codiceSconto);
                if (sconto == null) {
                    throw new OperationException("Codice sconto non valido.");
                }
                totale -= totale * sconto.getPercentuale() / 100;
            }

            String carta;
            if (cliente.getNumeroCarta() != null && !cliente.getNumeroCarta().isEmpty()) {
                carta = cliente.getNumeroCarta();
            } else {
                carta = input.cartaCredito;
            }

            if (carta == null || carta.length() < 12) {
                throw new OperationException("Carta non valida o non fornita.");
            }

            int idCarrello = nextIdCarrello++;

            OrdineEntity ordine = new OrdineEntity(0, new Date(System.currentTimeMillis()), totale,
                    "in_corso", input.nomeUtente, input.codiceSconto, input.indirizzo);

            ordiniTemporanei.put(idCarrello, ordine);
            carrelliInAttesa.put(idCarrello, dettagli);
            cartaCreditoTemporanea.put(idCarrello, carta);

            return new OutputOrdine(dettagli, totale, "Ordine in attesa di conferma", idCarrello);

        } catch (DAOException | DBConnectionException e) {
            throw new OperationException("Errore nella preparazione dell'ordine: " + e.getMessage());
        }
    }

    public String confermaOrdine(int idCarrello) throws OperationException {
        try {
            if (!ordiniTemporanei.containsKey(idCarrello)) {
                throw new OperationException("Carrello non trovato.");
            }

            OrdineEntity ordine = ordiniTemporanei.get(idCarrello);
            String carta = cartaCreditoTemporanea.get(idCarrello);

            if (!eseguiPagamento(carta, ordine.getCostoTotale())) {
                return "Pagamento rifiutato";
            }

            ordine.setStato("ordinata");
            ordine = OrdineDAO.creaOrdine(ordine);
            int ordineId = ordine.getIdOrdine();

            for (DettagliOrdineEntity d : carrelliInAttesa.get(idCarrello)) {
                d.setIdOrdine(ordineId);
                DettaglioOrdineDAO.aggiungiDettaglio(d);

                ProdottoEntity p = ProdottoDAO.leggiProdotto(d.getCodiceProdotto());
                p.setQuantitaDisponibile(p.getQuantitaDisponibile() - d.getQuantita());
                ProdottoDAO.aggiornaProdotto(p);
            }

            if (ordine.getCodiceSconto() != null) {
                ScontoDAO.segnaScontoComeUtilizzato(ordine.getCodiceSconto());
            }

            ClienteRegistratoDAO.incrementaNumeroOrdini(ordine.getNomeUtente());
            ClienteRegistratoEntity cliente = ClienteRegistratoDAO.leggiCliente(ordine.getNomeUtente());

            if (cliente != null) {
                System.out.println("Email inviata a " + cliente.getEmail());
                System.out.println("SMS inviato a " + cliente.getNumeroTelefono());

                if (cliente.getNumeroOrdini() >= SOGLIA_CLIENTE_ABITUALE) {
                    System.out.println("Cliente abituale confermato: " + cliente.getNomeUtente());
                }
            }

            ordiniTemporanei.remove(idCarrello);
            carrelliInAttesa.remove(idCarrello);
            cartaCreditoTemporanea.remove(idCarrello);

            return "Ordine confermato con successo! ID: " + ordineId;

        } catch (DAOException | DBConnectionException e) {
            throw new OperationException("Errore nella conferma dell'ordine: " + e.getMessage());
        }
    }

    public String annullaOrdine(int idCarrello) throws OperationException {
        if (!ordiniTemporanei.containsKey(idCarrello)) {
            throw new OperationException("Nessun ordine temporaneo corrispondente all'ID indicato.");
        }
        ordiniTemporanei.remove(idCarrello);
        carrelliInAttesa.remove(idCarrello);
        cartaCreditoTemporanea.remove(idCarrello);
        return "Carrello annullato con successo.";
    }

    private boolean eseguiPagamento(String cartaCredito, float importo) {
        return cartaCredito != null && cartaCredito.length() >= 12 && !cartaCredito.contains("0000");
    }
}
