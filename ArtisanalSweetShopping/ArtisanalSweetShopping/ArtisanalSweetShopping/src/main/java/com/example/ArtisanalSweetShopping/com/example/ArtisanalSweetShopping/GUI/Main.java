package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.GUI;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Benvenuti da Artisanal Sweet Shop");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Benvenuti da Artisanal Sweet Shop", SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 18));
        frame.add(titolo, BorderLayout.NORTH);

        JButton loginBtn = new JButton("Login");
        JButton registratiBtn = new JButton("Registrati");

        JPanel centro = new JPanel(new GridLayout(2, 1, 20, 20));
        centro.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));
        centro.add(loginBtn);
        centro.add(registratiBtn);
        frame.add(centro, BorderLayout.CENTER);

        loginBtn.addActionListener(e -> {
            frame.dispose();
            new GUILogin();
        });

        registratiBtn.addActionListener(e -> {
            frame.dispose();
            new GUIRegistrazione();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

