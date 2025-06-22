package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity;

import java.util.Date;

public class OrdineEntity {
    private int idOrdine;
    private Date data;
    private float costoTotale;
    private String stato;           // IN_CORSO, ORDINATA, CONSEGNATA
    private String codiceSconto;    
    private String nomeUtente;
    private String indirizzo;       

    public OrdineEntity(int idOrdine, Date data, float costoTotale, String stato, String nomeUtente, String codiceSconto, String indirizzo) {
        this.idOrdine = idOrdine;
        this.data = data;
        this.costoTotale = costoTotale;
        this.stato = stato;
        this.nomeUtente = nomeUtente;
        this.codiceSconto = codiceSconto;
        this.indirizzo = indirizzo;
    }


    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public float getCostoTotale() {
        return costoTotale;
    }

    public void setCostoTotale(float costoTotale) {
        this.costoTotale = costoTotale;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getCodiceSconto() {
        return codiceSconto;
    }

    public void setCodiceSconto(String codiceSconto) {
        this.codiceSconto = codiceSconto;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
}
