package restaurantsim.model;

import java.util.ArrayList;

public class Classifica {
    private ArrayList<String> classifica;

    public Classifica() {
        this.classifica = new ArrayList<>();
    }

    public ArrayList<String> getClassifica() {
        return classifica;
    }

    public void inserisciPartita(String nome, int punteggio) {
        classifica.add(String.format("Giocatore: %s --- Punteggio: %d", nome, punteggio));
    }
}
