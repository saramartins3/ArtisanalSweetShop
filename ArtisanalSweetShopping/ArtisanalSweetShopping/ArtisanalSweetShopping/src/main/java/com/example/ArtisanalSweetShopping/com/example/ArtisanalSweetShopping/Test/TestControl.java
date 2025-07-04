package com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Test;

import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control.AcquistoControl;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control.AcquistoControl.InputOrdine;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control.AcquistoControl.OutputOrdine;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control.AcquistoControl.OutputPagamentoFallito;
import com.example.ArtisanalSweetShopping.com.example.ArtisanalSweetShopping.Control.AcquistoControl.ProdottoQuantita;

import java.util.ArrayList;
import java.util.List;

public class TestControl {
    public static void main(String[] args) {
        InputOrdine input = new InputOrdine();
        input.nomeUtente = "andrea.bianchi"; 
        input.indirizzo = "Via Cardamomo 23";
        input.codiceSconto = "Sconto01";      
        input.cartaCredito = "4111111111111111";

        List<ProdottoQuantita> prodotti = new ArrayList<>();
        ProdottoQuantita p = new ProdottoQuantita();
        p.codiceProdotto = "P013";
        p.quantita = 2;
        prodotti.add(p);
        input.prodotti = prodotti;

        try {
            AcquistoControl control = new AcquistoControl();
            Object res = control.avviaOrdine(input);

            if (res instanceof OutputOrdine ord) {
                System.out.println("✅ Ordine preparato!");
                System.out.println("ID carrello: " + ord.idCarrello);
                System.out.println("Totale: €" + ord.totale);
                System.out.println("Messaggio: " + ord.messaggio);
            } else if (res instanceof OutputPagamentoFallito fail) {
                System.out.println("Pagamento fallito: " + fail.messaggio);
                System.out.println("Suggerimento: " + fail.suggerimento);
            } else {
                System.out.println("Risultato non riconosciuto.");
            }

        } catch (Exception e) {
            System.out.println("Errore durante il test:");
            e.printStackTrace();
        }
    }
}

