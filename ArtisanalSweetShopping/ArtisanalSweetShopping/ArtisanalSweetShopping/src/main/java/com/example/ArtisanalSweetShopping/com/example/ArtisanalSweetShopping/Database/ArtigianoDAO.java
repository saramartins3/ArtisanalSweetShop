package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DAOException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DBConnectionException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.ArtigianoEntity;

public class ArtigianoDAO {
    public static List<ArtigianoEntity> leggiTuttiArtigiani() throws DAOException, DBConnectionException {
        String query = "SELECT * FROM Artigiani";
        List<ArtigianoEntity> artigiani = new ArrayList<>();

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                artigiani.add(new ArtigianoEntity(
                        rs.getInt("IDArtigiano"),
                        rs.getString("Nome"),
                        rs.getString("Cognome")
                ));
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nella lettura degli artigiani");
        }

        return artigiani;
    }
}
