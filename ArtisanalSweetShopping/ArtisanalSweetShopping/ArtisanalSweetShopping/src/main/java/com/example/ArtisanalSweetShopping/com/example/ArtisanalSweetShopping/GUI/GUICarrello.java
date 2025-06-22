package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.GUI;

import javax.swing.*;

import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.GUI.GUIListaProdotti.GUIArticolo;

import java.awt.*;
import java.util.List;

public class GUICarrello {

    public GUICarrello(JFrame precedente, List<GUIArticolo> articoli) {
        JFrame frame = new JFrame("Carrello");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);

        final double[] totale = {0};

        for (GUIArticolo a : articoli) {
            area.append(a.toString() + "\n");
            totale[0] += a.totale();
        }
        area.append("\nTotale: €" + String.format("%.2f", totale[0]));

        JScrollPane scroll = new JScrollPane(area);

        JButton procedi = new JButton("Procedi");
        JButton annulla = new JButton("Annulla");

        JPanel bottoni = new JPanel();
        bottoni.add(procedi);
        bottoni.add(annulla);

        procedi.addActionListener(e -> {
            frame.dispose();
            new GUIPagamento(totale[0]);
        });

        annulla.addActionListener(e -> {
            frame.dispose();
            precedente.setVisible(true);
        });

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(bottoni, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        precedente.setVisible(false);
    }
}

