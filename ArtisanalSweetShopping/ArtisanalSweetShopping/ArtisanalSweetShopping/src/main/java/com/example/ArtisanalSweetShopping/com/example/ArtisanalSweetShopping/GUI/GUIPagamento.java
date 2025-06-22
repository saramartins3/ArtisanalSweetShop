package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.GUI;

import javax.swing.*;
import java.awt.*;

public class GUIPagamento {

    public GUIPagamento(double totale) {
        JFrame frame = new JFrame("Pagamento");
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout());

        JLabel titolo = new JLabel("Totale da pagare: €" + String.format("%.2f", totale), SwingConstants.CENTER);
        titolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        frame.add(titolo, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField indirizzo = new JTextField();
        JTextField carta = new JTextField();
        JButton conferma = new JButton("Conferma Ordine");

        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        form.add(new JLabel("Indirizzo di consegna:")); form.add(indirizzo);
        form.add(new JLabel("Numero carta:")); form.add(carta);
        form.add(new JLabel()); form.add(conferma);

        frame.add(form, BorderLayout.CENTER);

        conferma.addActionListener(e -> {
            if (indirizzo.getText().trim().isEmpty() || carta.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Compila tutti i campi.");
                return;
            }

            JOptionPane.showMessageDialog(frame, "Ordine confermato! Grazie per l'acquisto.");
            frame.dispose();
            new Main(); // Torna all'inizio
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
