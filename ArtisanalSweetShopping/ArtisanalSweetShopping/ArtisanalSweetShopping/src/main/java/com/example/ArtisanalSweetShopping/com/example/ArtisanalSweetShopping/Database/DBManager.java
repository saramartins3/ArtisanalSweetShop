package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

public class DBManager {
    private static Connection conn = null;
    private static final String CONFIG_FILE = "dbconfig.properties";

    private DBManager() {}

    public static Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try (InputStream input = DBManager.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
                Properties props = new Properties();
                if (input == null) {
                    throw new IOException("File di configurazione non trovato: " + CONFIG_FILE);
                }
                props.load(input);

                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                conn = DriverManager.getConnection(url, user, password);
            } catch (IOException e) {
                throw new SQLException("Errore nella lettura della configurazione del database", e);
            }
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            conn = null;
        }
    }
}
