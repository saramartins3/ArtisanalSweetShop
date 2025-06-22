package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.GUI;

import javax.swing.*;
import java.awt.*;

public class GUIRegistrazione {

    public GUIRegistrazione() {
        JFrame frame = new JFrame("Registrazione");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Registrazione Nuovo Cliente", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(titolo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        JTextField nomeField = new JTextField();
        JTextField cognomeField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registrati = new JButton("Registrati");

        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.add(new JLabel("Nome:")); form.add(nomeField);
        form.add(new JLabel("Cognome:")); form.add(cognomeField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Telefono:")); form.add(telefonoField);
        form.add(new JLabel("Nome utente:")); form.add(usernameField);
        form.add(new JLabel("Password:")); form.add(passwordField);
        form.add(new JLabel()); form.add(registrati);
        frame.add(form, BorderLayout.CENTER);

        registrati.addActionListener(e -> {
            // TODO: Salva utente nel database
            JOptionPane.showMessageDialog(frame, "Registrazione completata con successo 🎉");
            frame.dispose();
            new GUILogin();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
