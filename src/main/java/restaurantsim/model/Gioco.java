package restaurantsim.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

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
     * Il semaforo per sincronizzare l'accesso alla lista delle notifiche, che contiene le notifiche da mostrare al giocatore.
     */
    private Semaphore mutexNotifiche;
    /**
     * La lista delle notifiche da mostrare al giocatore, con le notifiche più recenti in cima alla lista.
     * 
     * L'accesso a questa lista è sincronizzato tramite il semaforo {@link #mutexNotifiche}.
     */
    private LinkedList<Notifica> notifiche;

    /**
     * La lista dei cuochi, che contiene lo stato di ogni cuoco (il piatto che sta preparando e il tempo rimanente per completarlo).
     */
    private ArrayList<Cuoco> cuochi;

    /**
     * Inizializza lo stato del gioco.
     */
    public Gioco() {
        sala = new Sala();

        punteggio = 0;
        clientiArrabbiati = 0;

        mutexNotifiche = new Semaphore(1);
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
     * Registra una nuova notifica da mostrare al giocatore, inserendola in cima alla lista delle notifiche.
     * 
     * Se la lista supera le 10 notifiche, la più vecchia viene rimossa.
     * 
     * @param notifica la notifica da registrare
     * @throws InterruptedException se il thread viene interrotto mentre si registra la notifica.
     */
    public void registraNotifica(Notifica notifica) throws InterruptedException {
        mutexNotifiche.acquire();

        notifiche.addFirst(notifica);
        if (notifiche.size() == 11)
            notifiche.removeLast();

        mutexNotifiche.release();
    }

    /**
     * Resetta lo stato del gioco.
     */
    public void reset() {
        // Aspettiamo che eventuali operazioni su notifiche in corso finiscano prima di resettare tutto
        mutexNotifiche.acquireUninterruptibly();

        sala.reset();
        punteggio = 0;
        clientiArrabbiati = 0;

        notifiche.clear();
        mutexNotifiche.release();

        for (Cuoco cuoco : cuochi) {
            cuoco.reset();
        }

        mutexNotifiche.release();
    }
}
