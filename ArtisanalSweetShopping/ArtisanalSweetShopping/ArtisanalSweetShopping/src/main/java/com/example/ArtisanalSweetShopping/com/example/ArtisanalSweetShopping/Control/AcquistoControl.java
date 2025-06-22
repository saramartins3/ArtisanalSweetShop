package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.OperationsException;
import javax.swing.table.DefaultTableCellRenderer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DAOException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DBConnectionException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.OperationException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.DettagliOrdineEntity;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database.DettaglioOrdineDAO;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.ProdottoEntity;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.ScontoEntity;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.ClienteRegistratoEntity;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.OrdineEntity;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database.ClienteRegistratoDAO;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database.OrdineDAO;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database.ProdottoDAO;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database.ScontoDAO;

@RestController
public class AcquistoControl {

    private static final Map<Integer, List<DettagliOrdineEntity>> carrelliInAttesa = new HashMap<>();
    private static final Map<Integer, OrdineEntity> ordiniTemporanei = new HashMap<>();
    private static final Map<Integer, String> cartaCreditoTemporanea = new HashMap<>();
    private static int nextIdCarrello = 1;
    private static final int SOGLIA_CLIENTE_ABITUALE = 3;

    // DTO
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
        public String suggerimento;

        public OutputPagamentoFallito(String messaggio, String suggerimento) {
            this.messaggio = messaggio;
            this.suggerimento = suggerimento;
        }
    }

    // Endpoint per avviare ordine
    @PostMapping("/checkout")
    public Object avviaOrdine(@RequestBody InputOrdine input) throws OperationException {
        try {
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

            if (input.codiceSconto != null && !input.codiceSconto.isEmpty()) {
                ScontoEntity sconto = ScontoDAO.leggiScontoValido(input.codiceSconto);
                if (sconto == null) {
                    return new OutputPagamentoFallito("Codice sconto non valido.", "Puoi riprovare con un altro codice o procedere senza sconto.");
                }
                totale -= totale * sconto.getPercentuale() / 100;
            }

            ClienteRegistratoEntity cliente = ClienteRegistratoDAO.leggiCliente(input.nomeUtente);
            String carta = (cliente != null && cliente.getNumeroTelefono() != null)
                    ? cliente.getNumeroCarta()
                    : input.cartaCredito;

            if (carta == null || carta.length() < 12) {
                return new OutputPagamentoFallito("Carta di credito non valida o non fornita.", "Inserisci una carta valida per procedere all’acquisto.");
            }

            int id = nextIdCarrello++;
            OrdineEntity ordine = new OrdineEntity(0, new Date(System.currentTimeMillis()), totale, "IN_CORSO", input.nomeUtente, input.codiceSconto, input.indirizzo);

            ordiniTemporanei.put(id, ordine);
            carrelliInAttesa.put(id, dettagli);
            cartaCreditoTemporanea.put(id, carta);

            return new OutputOrdine(dettagli, totale, "Ordine preparato, pronto alla conferma", id);

        } catch (DAOException | DBConnectionException e) {
            throw new OperationException("Errore durante la preparazione dell’ordine.");
        }
    }

    // Endpoint per confermare ordine
    @PostMapping("/checkout/conferma/{id}")
    public Object confermaOrdine(@PathVariable int id) throws OperationException {
        try {
            if (!ordiniTemporanei.containsKey(id)) {
                return new OutputPagamentoFallito("Carrello non trovato", "Ricomincia il checkout.");
            }

            OrdineEntity ordine = ordiniTemporanei.get(id);
            String carta = cartaCreditoTemporanea.get(id);

            if (!PagamentoService.eseguiPagamento(carta, ordine.getCostoTotale())) {
                return new OutputPagamentoFallito("Pagamento rifiutato.", "Puoi riprovare il pagamento o annullare l’acquisto.");
            }

            ordine.setStato("ORDINATA");
            OrdineDAO.creaOrdine(ordine);

            for (DettagliOrdineEntity d : carrelliInAttesa.get(id)) {
                d.setIdOrdine(ordine.getIdOrdine());
                DettaglioOrdineDAO.aggiungiDettaglio(d);

                ProdottoEntity prod = ProdottoDAO.leggiProdotto(d.getCodiceProdotto());
                prod.setQuantitaDisponibile(prod.getQuantitaDisponibile() - d.getQuantita());
                ProdottoDAO.aggiornaProdotto(prod);
            }

            if (ordine.getCodiceSconto() != null) {
                ScontoDAO.segnaScontoComeUtilizzato(ordine.getCodiceSconto());
            }

            ClienteRegistratoEntity cliente = ClienteRegistratoDAO.leggiCliente(ordine.getNomeUtente());
            if (cliente != null) {
                ClienteRegistratoDAO.incrementaNumeroOrdini(cliente.getNomeUtente());
                System.out.println("Email inviata a " + cliente.getEmail());
                System.out.println("SMS inviato a " + cliente.getNumeroTelefono());

                if (cliente.getNumeroOrdini() + 1 >= SOGLIA_CLIENTE_ABITUALE) {
                    System.out.println("Cliente " + cliente.getNomeUtente() + " ora è cliente abituale!");
                }
            }

            System.out.println("Notifica al fattorino per l’ordine #" + ordine.getIdOrdine());

            // Pulizia
            ordiniTemporanei.remove(id);
            carrelliInAttesa.remove(id);
            cartaCreditoTemporanea.remove(id);

            return "Ordine confermato con successo! ID ordine: " + ordine.getIdOrdine();

        } catch (DAOException | DBConnectionException e) {
            throw new OperationException("Errore nella conferma dell’ordine: " + e.getMessage());
        }
    }

    // Endpoint per annullare ordine
    @PostMapping("/checkout/annulla/{id}")
    public String annullaOrdine(@PathVariable int id) throws OperationException {
        if (!ordiniTemporanei.containsKey(id)) {
            throw new OperationException("ID carrello non valido o già confermato.");
        }
        ordiniTemporanei.remove(id);
        carrelliInAttesa.remove(id);
        cartaCreditoTemporanea.remove(id);
        return "Carrello annullato correttamente.";
    }

    // Simulazione servizio di pagamento
    static class PagamentoService {
        public static boolean eseguiPagamento(String cartaCredito, float importo) {
            return cartaCredito != null && cartaCredito.length() >= 12 && !cartaCredito.contains("0000");
        }
    }
}
