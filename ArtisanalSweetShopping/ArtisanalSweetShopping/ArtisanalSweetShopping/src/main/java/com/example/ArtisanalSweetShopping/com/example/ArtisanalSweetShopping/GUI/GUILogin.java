package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.GUI;

import javax.swing.*;
import java.awt.*;

public class GUILogin {

    public GUILogin() {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 220);
        frame.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Login Cliente", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(titolo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton accedi = new JButton("Accedi");

        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.add(new JLabel("Nome utente:")); form.add(usernameField);
        form.add(new JLabel("Password:")); form.add(passwordField);
        form.add(new JLabel()); form.add(accedi);
        frame.add(form, BorderLayout.CENTER);

        accedi.addActionListener(e -> {
            String nomeUtente = usernameField.getText().trim();
            String pass = new String(passwordField.getPassword());

            if (!nomeUtente.isEmpty() && !pass.isEmpty()) {
                // TODO: Verifica con database
                frame.dispose();
                new GUIListaProdotti(nomeUtente);
            } else {
                JOptionPane.showMessageDialog(frame, "Inserisci nome utente e password.");
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
