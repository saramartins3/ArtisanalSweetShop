package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity;

public class ProdottoEntity {
    private String codiceProdotto;
    private String nome;
    private String descrizione;
    private float prezzo;
    private String foto;
    private int quantitaDisponibile;
    private int idCategoria;

    public ProdottoEntity(String codiceProdotto, String nome, String descrizione, float prezzo, String foto,
                          int quantitaDisponibile, int idCategoria) {
        this.codiceProdotto = codiceProdotto;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.foto = foto;
        this.quantitaDisponibile = quantitaDisponibile;
        this.idCategoria = idCategoria;
    }

    public String getCodiceProdotto() {
        return codiceProdotto;
    }

    public void setCodiceProdotto(String codiceProdotto) {
        this.codiceProdotto = codiceProdotto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }

    public void setQuantitaDisponibile(int quantitaDisponibile) {
        this.quantitaDisponibile = quantitaDisponibile;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
}
