package restaurantsim.model;

/**
 * Eccezione che viene lanciata quando si tenta di servire un piatto errato al tavolo, ovvero un piatto diverso da quello ordinato dai clienti al tavolo.
 */
public class PiattoErratoException extends Exception {
    /**
     * Costruisce una nuova eccezione con il messaggio specificato.
     * 
     * @param message il messaggio da associare all'eccezione
     */
    public PiattoErratoException(String message) {
        super(message);
    }
}
