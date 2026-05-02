package restaurantsim.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto che rappresenta la classifica dei giocatori, con i loro punteggi.
 * 
 * I dati qui salvati non sono thread-safe! Devono essere usati solo sull'EDT
 */
public class Classifica {
    /**
     * Lista che contiene le stringhe con i nomi dei giocatori e i loro punteggi.
     */
    private ArrayList<String> classifica;

    /**
     * Inizializza la classifica.
     */
    public Classifica() {
        this.classifica = new ArrayList<>();
    }

    /**
     * Restituisce la classifica dei giocatori.
     * 
     * @return la classifica dei giocatori, con i loro nomi e punteggi
     */
    public List<String> getClassifica() {
        return classifica;
    }

    /**
     * Svuota la classifica corrente.
     */
    public void pulisciClassifica() {
        classifica.clear();
    }

    /**
     * Aggiunge tutti gli elementi di una lista alla classifica attuale.
     * 
     * @param classificaList La lista di stringhe da aggiungere alla classifica.
     */
    public void aggiungiTutto(List<String> classificaList) {
        classifica.addAll(classificaList);
    }

    /**
     * Inserisce una partita nella classifica, formattando il nome del giocatore e il suo punteggio.
     * 
     * @param nome      il nome del giocatore
     * @param punteggio il punteggio del giocatore
     */
    public void inserisciPartita(String nome, int punteggio) {
        classifica.add(String.format("Giocatore: %s --- Punteggio: %d", nome, punteggio));
    }
}
