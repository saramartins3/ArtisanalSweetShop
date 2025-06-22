package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity;

public class ClienteRegistratoEntity {
    private String nomeUtente;
    private String password;
    private String email;
    private String numeroTelefono;
    private int numeroOrdini;
    private String numeroCarta;

    public ClienteRegistratoEntity(String nomeUtente, String password, String email, String numeroTelefono, int numeroOrdini, String numeroCarta) {
        this.nomeUtente = nomeUtente;
        this.password = password;
        this.email = email;
        this.numeroTelefono = numeroTelefono;
        this.numeroOrdini = numeroOrdini;
        this.numeroCarta = numeroCarta;
    }

    public String getNomeUtente() {
        return nomeUtente;
    }

    public void setNomeUtente(String nomeUtente) {
        this.nomeUtente = nomeUtente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public int getNumeroOrdini() {
        return numeroOrdini;
    }

    public void setNumeroOrdini(int numeroOrdini) {
        this.numeroOrdini = numeroOrdini;
    }
    
    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    // Metodo calcolato per determinare se il cliente è abituale
    public boolean isClienteAbituale(int sogliaOrdini) {
        return this.numeroOrdini >= sogliaOrdini;
    }
}

