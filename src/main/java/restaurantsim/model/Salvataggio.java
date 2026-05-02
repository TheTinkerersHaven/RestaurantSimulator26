package restaurantsim.model;

import java.util.List;

/**
 * Rappresenta lo stato salvato di una partita, utilizzato per la persistenza su file JSON.
 */
public class Salvataggio {
    /**
     * Il punteggio attuale del giocatore.
     */
    private int punteggio;
    /**
     * Il numero di clienti che si sono arrabbiati durante la partita.
     */
    private int clientiArrabbiati;
    /**
     * La lista delle notifiche recenti.
     */
    private List<Notifica> notifiche;
    /**
     * Lo stato dei cuochi.
     */
    private List<Cuoco> cuochi;
    /**
     * La lista dei piatti pronti in sala.
     */
    private List<Piatto> piattiPronti;
    /**
     * Lo stato dei tavoli nel ristorante.
     */
    private List<Tavolo> tavoli;

    /**
     * Costruttore vuoto necessario per la deserializzazione Jackson.
     */
    public Salvataggio() {}

    /**
     * Crea un nuovo oggetto Salvataggio estraendo lo stato corrente da un oggetto Gioco.
     * 
     * @param gioco L'istanza del gioco da cui salvare i dati.
     */
    public Salvataggio(Gioco gioco) {
        this.punteggio = gioco.getPunteggio();
        this.clientiArrabbiati = gioco.getClientiArrabbiati();
        this.notifiche = gioco.getNotifiche();
        this.cuochi = gioco.getCuochi();
        this.piattiPronti = gioco.getSala().getPiattiPronti();
        this.tavoli = gioco.getSala().getTavoli();
    }

    /**
     * Restituisce il punteggio salvato.
     * 
     * @return il punteggio.
     */
    public int getPunteggio() {
        return punteggio;
    }

    /**
     * Imposta il punteggio per il salvataggio.
     * 
     * @param punteggio il punteggio da impostare.
     */
    public void setPunteggio(int punteggio) {
        this.punteggio = punteggio;
    }

    /**
     * Restituisce il numero di clienti arrabbiati salvato.
     * 
     * @return il numero di clienti arrabbiati.
     */
    public int getClientiArrabbiati() {
        return clientiArrabbiati;
    }

    /**
     * Imposta il numero di clienti arrabbiati.
     * 
     * @param clientiArrabbiati il numero da impostare.
     */
    public void setClientiArrabbiati(int clientiArrabbiati) {
        this.clientiArrabbiati = clientiArrabbiati;
    }

    /**
     * Restituisce la lista delle notifiche salvate.
     * 
     * @return la lista delle notifiche.
     */
    public List<Notifica> getNotifiche() {
        return notifiche;
    }

    /**
     * Imposta la lista delle notifiche.
     * 
     * @param notifiche le notifiche da impostare.
     */
    public void setNotifiche(List<Notifica> notifiche) {
        this.notifiche = notifiche;
    }

    /**
     * Restituisce la lista dei cuochi salvata.
     * 
     * @return la lista dei cuochi.
     */
    public List<Cuoco> getCuochi() {
        return cuochi;
    }

    /**
     * Imposta la lista dei cuochi.
     * 
     * @param cuochi i cuochi da impostare.
     */
    public void setCuochi(List<Cuoco> cuochi) {
        this.cuochi = cuochi;
    }

    /**
     * Restituisce la lista dei piatti pronti salvata.
     * 
     * @return la lista dei piatti pronti.
     */
    public List<Piatto> getPiattiPronti() {
        return piattiPronti;
    }

    /**
     * Imposta la lista dei piatti pronti.
     * 
     * @param piattiPronti i piatti da impostare.
     */
    public void setPiattiPronti(List<Piatto> piattiPronti) {
        this.piattiPronti = piattiPronti;
    }

    /**
     * Restituisce la lista dei tavoli salvata.
     * 
     * @return la lista dei tavoli.
     */
    public List<Tavolo> getTavoli() {
        return tavoli;
    }

    /**
     * Imposta la lista dei tavoli.
     * 
     * @param tavoli i tavoli da impostare.
     */
    public void setTavoli(List<Tavolo> tavoli) {
        this.tavoli = tavoli;
    }
}
