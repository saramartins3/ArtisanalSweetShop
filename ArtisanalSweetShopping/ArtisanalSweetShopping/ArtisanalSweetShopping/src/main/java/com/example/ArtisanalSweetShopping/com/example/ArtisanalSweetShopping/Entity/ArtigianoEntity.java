package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity;

public class ArtigianoEntity {
    private int idArtigiano;
    private String nome;
    private String cognome;

    public ArtigianoEntity(int idArtigiano, String nome, String cognome) {
        this.idArtigiano = idArtigiano;
        this.nome = nome;
        this.cognome = cognome;
    }

    public int getIdArtigiano() {
        return idArtigiano;
    }

    public void setIdArtigiano(int idArtigiano) {
        this.idArtigiano = idArtigiano;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
