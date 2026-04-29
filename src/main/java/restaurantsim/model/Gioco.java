package restaurantsim.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Gioco {
    public static final int NUM_CUOCHI = 3;

    public static final int PUNTEGGIO_PER_PIATTO = 10;
    public static final int PUNTEGGIO_PER_CLIENTE_ARRABBIATO = -5;

    public static final int MAX_CLIENTI_ARRABBIATI = 10;

    private Sala sala;

    private int punteggio;
    private int clientiArrabbiati;

    private Semaphore mutexNotifiche;
    private LinkedList<Notifica> notifiche;

    private ArrayList<Cuoco> cuochi;

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

    public Sala getSala() {
        return sala;
    }

    public int getPunteggio() {
        return punteggio;
    }

    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    public void aggiungiPunteggio(int punti) {
        this.punteggio += punti;
    }

    public int getClientiArrabbiati() {
        return clientiArrabbiati;
    }

    public void setClientiArrabbiati(int clientiArrabbiati) {
        this.clientiArrabbiati = clientiArrabbiati;
    }

    public void incrementaClientiArrabbiati() {
        this.clientiArrabbiati++;
    }

    public List<Notifica> getNotifiche() {
        return notifiche;
    }

    public Cuoco getCuoco(int numeroCuoco) {
        return cuochi.get(numeroCuoco - 1);
    }

    public List<Cuoco> getCuochi() {
        return cuochi;
    }

    public void registraNotifica(Notifica notif) throws InterruptedException {
        mutexNotifiche.acquire();

        notifiche.addFirst(notif);
        if (notifiche.size() == 11)
            notifiche.removeLast();

        mutexNotifiche.release();
    }

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
