package restaurantsim.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Oggetto principale che rappresenta lo stato del gioco. Contiene la sala, i cuochi, il punteggio, il numero di clienti arrabbiati e le notifiche da mostrare al giocatore.
 */
public class Gioco {
    /**
     * Il numero di cuochi disponibili nel gioco.
     */
    public static final int NUM_CUOCHI = 3;
    /**
     * Il numero di tavoli disponibili nel gioco.
     */
    public static final int NUM_TAVOLI = 4;

    /**
     * Il punteggio assegnato per ogni piatto consegnato correttamente al cliente.
     */
    public static final int PUNTEGGIO_PER_PIATTO = 10;
    /**
     * Il punteggio sottratto per ogni cliente che si arrabbia a causa di un ordine non consegnato in tempo.
     */
    public static final int PUNTEGGIO_PER_CLIENTE_ARRABBIATO = -5;

    /**
     * Il numero massimo di clienti arrabbiati prima che il gioco finisca. Se il numero di clienti arrabbiati raggiunge questo valore, il gioco termina con una sconfitta.
     */
    public static final int MAX_CLIENTI_ARRABBIATI = 10;

    /**
     * La sala del ristorante, che contiene i tavoli e i piatti pronti.
     */
    private Sala sala;

    /**
     * Il punteggio attuale del giocatore.
     */
    private int punteggio;
    /**
     * Il numero di clienti arrabbiati.
     */
    private int clientiArrabbiati;

    /**
     * La lista delle notifiche da mostrare al giocatore, con le notifiche più recenti in cima alla lista.
     * 
     * L'accesso a questa lista non è thread-safe! Deve essere usato solo sull'EDT
     */
    private LinkedList<Notifica> notifiche;

    /**
     * La lista dei cuochi, che contiene lo stato di ogni cuoco (il piatto che sta preparando e il tempo rimanente per completarlo).
     * 
     * L'accesso a questa lista non è thread-safe! Deve essere usato solo sull'EDT
     */
    private ArrayList<Cuoco> cuochi;

    /**
     * Inizializza lo stato del gioco.
     */
    public Gioco() {
        sala = new Sala();

        punteggio = 0;
        clientiArrabbiati = 0;

        notifiche = new LinkedList<>();

        cuochi = new ArrayList<>(NUM_CUOCHI);
        for (int i = 0; i < NUM_CUOCHI; i++) {
            cuochi.add(new Cuoco());
        }
    }

    /**
     * Restituisce la sala del ristorante.
     * 
     * @return la sala del ristorante
     */
    public Sala getSala() {
        return sala;
    }

    /**
     * Restituisce il punteggio attuale del giocatore.
     * 
     * @return il punteggio attuale del giocatore
     */
    public int getPunteggio() {
        return punteggio;
    }

    /**
     * Imposta il punteggio attuale del giocatore.
     * 
     * @param punteggio il punteggio da impostare
     */
    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    /**
     * Aggiunge punti al punteggio attuale del giocatore.
     * 
     * @param punti i punti da aggiungere al punteggio attuale del giocatore
     */
    public void aggiungiPunteggio(int punti) {
        this.punteggio += punti;
    }

    /**
     * Restituisce il numero di clienti arrabbiati.
     * 
     * @return il numero di clienti arrabbiati
     */
    public int getClientiArrabbiati() {
        return clientiArrabbiati;
    }

    /**
     * Imposta il numero di clienti arrabbiati.
     * 
     * @param clientiArrabbiati il numero di clienti arrabbiati da impostare
     */
    public void setClientiArrabbiati(int clientiArrabbiati) {
        this.clientiArrabbiati = clientiArrabbiati;
    }

    /**
     * Incrementa il numero di clienti arrabbiati di 1.
     */
    public void incrementaClientiArrabbiati() {
        this.clientiArrabbiati++;
    }

    /**
     * Restituisce la lista delle notifiche da mostrare al giocatore, con le notifiche più recenti in cima alla lista.
     * 
     * @return la lista delle notifiche da mostrare al giocatore
     */
    public List<Notifica> getNotifiche() {
        return notifiche;
    }

    /**
     * Restituisce il cuoco con il numero specificato.
     * 
     * @param numeroCuoco il numero del cuoco da restituire
     * @return il cuoco con il numero specificato
     */
    public Cuoco getCuoco(int numeroCuoco) {
        return cuochi.get(numeroCuoco - 1);
    }

    /**
     * Restituisce l'ArrayList dei cuochi.
     * 
     * @return l'ArrayList dei cuochi.
     */
    public List<Cuoco> getCuochi() {
        return cuochi;
    }

    /**
     * Registra una nuova notifica da mostrare al giocatore, inserendola in cima alla lista delle notifiche.
     * 
     * Se la lista supera le 10 notifiche, la più vecchia viene rimossa.
     * 
     * @param notifica la notifica da registrare
     */
    public void registraNotifica(Notifica notifica) {
        notifiche.addFirst(notifica);
        if (notifiche.size() == 11)
            notifiche.removeLast();
    }

    /**
     * Rimuove una notifica dalla lista.
     * 
     * @param index L'indice della notifica da rimuovere.
     */
    public void rimuoviNotifica(int index) {
        notifiche.remove(index);
    }

    /**
     * Restituisce una notifica specifica.
     * 
     * @param index L'indice della notifica da restituire.
     * @return La notifica all'indice specificato.
     */
    public Notifica getNotifica(int index) {
        return notifiche.get(index);
    }

    /**
     * Rimuove tutte le notifiche registrate.
     */
    public void cancellaNotifiche() {
        notifiche.clear();
    }

    /**
     * Resetta lo stato del gioco.
     * 
     * @throws InterruptedException se il thread viene interrotto durante il reset della sala.
     */
    public void reset() throws InterruptedException {
        sala.reset();
        punteggio = 0;
        clientiArrabbiati = 0;

        notifiche.clear();

        for (Cuoco cuoco : cuochi) {
            cuoco.reset();
        }
    }

    /**
     * Carica lo stato del gioco da un oggetto Salvataggio.
     *
     * @param salvataggio l'oggetto contenente i dati da caricare
     * @throws InterruptedException se il thread viene interrotto durante il caricamento dello stato dei tavoli.
     */
    public void carica(Salvataggio salvataggio) throws InterruptedException {
        this.punteggio = salvataggio.getPunteggio();
        this.clientiArrabbiati = salvataggio.getClientiArrabbiati();
        this.notifiche = new LinkedList<>(salvataggio.getNotifiche());
        for (int i = 0; i < this.cuochi.size(); i++) {
            this.cuochi.get(i).caricaCuoco(salvataggio.getCuochi().get(i));
        }
        this.sala.setPiattiPronti(new ArrayList<>(salvataggio.getPiattiPronti()));
        this.sala.caricaTavoli(new ArrayList<>(salvataggio.getTavoli()));
    }
}
