package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DAOException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Exception.DBConnectionException;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Entity.ProdottoEntity;

public class ProdottoDAO {

    public static void creaProdotto(ProdottoEntity prodotto) throws DAOException, DBConnectionException {
        String query = "INSERT INTO Prodotti (CodiceProdotto, Nome, Prezzo, QuantitaDisponibile, ID_Categoria, Descrizione, Foto) " +
                       "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prodotto.getCodiceProdotto());
            stmt.setString(2, prodotto.getNome());
            stmt.setFloat(3, prodotto.getPrezzo());
            stmt.setInt(4, prodotto.getQuantitaDisponibile());
            stmt.setInt(5, prodotto.getIdCategoria());
            stmt.setString(6, prodotto.getDescrizione());
            stmt.setString(7, prodotto.getFoto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore durante la creazione del prodotto");
        }
    }

    public static ProdottoEntity leggiProdotto(String codiceProdotto) throws DAOException, DBConnectionException {
        String query = "SELECT * FROM Prodotti WHERE CodiceProdotto = ?";
        ProdottoEntity prodotto = null;

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, codiceProdotto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    prodotto = new ProdottoEntity(
                        rs.getString("CodiceProdotto"),
                        rs.getString("Nome"),
                        rs.getString("Descrizione"),
                        rs.getFloat("Prezzo"),
                        rs.getString("Foto"),
                        rs.getInt("QuantitaDisponibile"),
                        rs.getInt("ID_Categoria")
                    );
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nella lettura del prodotto");
        }

        return prodotto;
    }

    public static List<ProdottoEntity> leggiTuttiProdotti() throws DAOException, DBConnectionException {
        String query = "SELECT * FROM Prodotti";
        List<ProdottoEntity> prodotti = new ArrayList<>();

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                prodotti.add(new ProdottoEntity(
                    rs.getString("CodiceProdotto"),
                    rs.getString("Nome"),
                    rs.getString("Descrizione"),
                    rs.getFloat("Prezzo"),
                    rs.getString("Foto"),
                    rs.getInt("QuantitaDisponibile"),
                    rs.getInt("ID_Categoria")
                ));
            }

        } catch (SQLException e) {
            throw new DAOException("Errore nella lettura dei prodotti");
        }

        return prodotti;
    }

    public static void aggiornaProdotto(ProdottoEntity prodotto) throws DAOException, DBConnectionException {
        String query = "UPDATE Prodotti SET Nome = ?, Prezzo = ?, QuantitaDisponibile = ?, ID_Categoria = ?, Descrizione = ?, Foto = ? " +
                       "WHERE CodiceProdotto = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prodotto.getNome());
            stmt.setFloat(2, prodotto.getPrezzo());
            stmt.setInt(3, prodotto.getQuantitaDisponibile());
            stmt.setInt(4, prodotto.getIdCategoria());
            stmt.setString(5, prodotto.getDescrizione());
            stmt.setString(6, prodotto.getFoto());
            stmt.setString(7, prodotto.getCodiceProdotto());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore durante l'aggiornamento del prodotto");
        }
    }

    public static void eliminaProdotto(String codiceProdotto) throws DAOException, DBConnectionException {
        String query = "DELETE FROM Prodotti WHERE CodiceProdotto = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, codiceProdotto);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Errore durante l'eliminazione del prodotto");
        }
    }
}
