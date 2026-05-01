package restaurantsim.model;

import java.util.ArrayList;

/**
 * Oggetto che rappresenta la classifica dei giocatori, con i loro punteggi.
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
    public ArrayList<String> getClassifica() {
        return classifica;
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
