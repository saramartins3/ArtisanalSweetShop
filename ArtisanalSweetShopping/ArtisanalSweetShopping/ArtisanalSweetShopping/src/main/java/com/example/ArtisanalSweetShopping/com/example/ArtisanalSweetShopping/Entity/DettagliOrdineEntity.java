package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity;

public class DettagliOrdineEntity {
    private int idOrdine;          // FK -> Ordini.IDOrdine
    private String codiceProdotto; // FK -> Prodotti.CodiceProdotto
    private int quantita;

    public DettagliOrdineEntity(int idOrdine, String codiceProdotto, int quantita) {
        this.idOrdine = idOrdine;
        this.codiceProdotto = codiceProdotto;
        this.quantita = quantita;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getCodiceProdotto() {
        return codiceProdotto;
    }

    public void setCodiceProdotto(String codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
