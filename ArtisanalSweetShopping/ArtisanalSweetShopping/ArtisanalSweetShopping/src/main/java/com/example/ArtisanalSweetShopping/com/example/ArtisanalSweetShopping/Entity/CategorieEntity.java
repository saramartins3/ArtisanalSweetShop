package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity;

public class CategorieEntity {
    private int idCategoria;
    private String nome;

    public CategorieEntity(int idCategoria, String nome) {
        this.idCategoria = idCategoria;
        this.nome = nome;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}