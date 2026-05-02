package restaurantsim.model;

/**
 * Snapshot dello stato di un tavolo.
 * 
 * Read-only e quindi thread-safe
 */
public class StatoTavolo {
    private int numeroTavolo;
    private boolean occupato;
    private Piatto piattoOrdinato;
    private int pazienza;

    /**
     * Costruttore vuoto necessario per la deserializzazione JSON.
     */
    public StatoTavolo() {
    }

    /**
     * Crea un nuovo snapshot dello stato di un tavolo.
     * 
     * @param numeroTavolo   Il numero del tavolo.
     * @param occupato       Se il tavolo è occupato.
     * @param piattoOrdinato Il piatto ordinato.
     * @param pazienza       La pazienza rimanente dei clienti.
     */
    public StatoTavolo(int numeroTavolo, boolean occupato, Piatto piattoOrdinato, int pazienza) {
        this.numeroTavolo = numeroTavolo;
        this.occupato = occupato;
        this.piattoOrdinato = piattoOrdinato;
        this.pazienza = pazienza;
    }

    /**
     * Restituisce il numero del tavolo.
     * 
     * @return il numero del tavolo.
     */
    public int getNumeroTavolo() {
        return numeroTavolo;
    }

    /**
     * Restituisce se il tavolo è occupato.
     * 
     * @return true se occupato, false altrimenti.
     */
    public boolean isOccupato() {
        return occupato;
    }

    /**
     * Restituisce il piatto ordinato.
     * 
     * @return il piatto ordinato.
     */
    public Piatto getPiattoOrdinato() {
        return piattoOrdinato;
    }

    /**
     * Restituisce la pazienza dei clienti.
     * 
     * @return la pazienza (0-100).
     */
    public int getPazienza() {
        return pazienza;
    }
}
