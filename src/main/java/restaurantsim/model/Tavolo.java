package restaurantsim.model;

import java.util.Random;

/**
 * Rappresenta un tavolo del ristorante.
 */
public class Tavolo {
	/**
	 * Il numero del tavolo, che identifica univocamente il tavolo all'interno della sala.
	 */
	private int numeroTavolo;
	/**
	 * Il piatto ordinato al tavolo, che rappresenta il piatto che i clienti al tavolo hanno ordinato. Se il tavolo è vuoto, questo valore è {@link Piatto#NESSUNO}.
	 */
	private Piatto piattoOrdinato;
	/**
	 * La pazienza dei clienti al tavolo, che rappresenta quanto tempo i clienti al tavolo sono disposti ad aspettare prima di arrabbiarsi.
	 */
	private int pazienza;
	/**
	 * Indica se il tavolo è occupato o meno.
	 */
	private boolean occupato;
	/**
	 * Generatore di numeri casuali per determinare il piatto ordinato.
	 */
	private Random random = new Random();

	/**
	 * Inizializza un oggetto tavolo vuoto. Necessario per Jackson.
	 */
	public Tavolo() {}

	/**
	 * Inizializza un tavolo con il numero specificato, impostando lo stato iniziale del tavolo come vuoto, con pazienza al massimo e non occupato.
	 * 
	 * @param numeroTavolo il numero del tavolo da inizializzare
	 */
	public Tavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
		this.piattoOrdinato = Piatto.NESSUNO;
		this.pazienza = 100;
		this.occupato = false;
	}

	/**
	 * Fai arrivare i clienti al tavolo, impostando lo stato del tavolo come occupato, assegnando un piatto ordinato casuale e resettando la pazienza al massimo.
	 * 
	 * @throws TavoloOccupatoException se il tavolo è già occupato, poiché non è possibile far arrivare clienti a un tavolo già occupato.
	 */
	public void faiArrivareClienti() throws TavoloOccupatoException {
		if (occupato) {
			throw new TavoloOccupatoException();
		}

		this.occupato = true;
		this.pazienza = 100;

		int scelta = random.nextInt(3);
		switch (scelta) {
			case 0:
				this.piattoOrdinato = Piatto.SASHIMI;
				break;
			case 1:
				this.piattoOrdinato = Piatto.URAMAKI_RAINBOW;
				break;
			case 2:
				this.piattoOrdinato = Piatto.HOSOMAKI_MAGURO;
				break;
		}
	}

	/**
	 * Decrementa la pazienza dei clienti al tavolo di 1 punto.
	 * 
	 * @return true se i clienti si sono arrabbiati (pazienza raggiunta 0), false altrimenti
	 * @throws TavoloNonOccupatoException se il tavolo è vuoto, poiché non è possibile decrementare la pazienza di un tavolo vuoto.
	 */
	public boolean decrementaPazienza() throws TavoloNonOccupatoException {
		if (!occupato) {
			throw new TavoloNonOccupatoException();
		}

		pazienza -= 1;

		if (pazienza <= 0) {
			occupato = false;
			piattoOrdinato = Piatto.NESSUNO;
			pazienza = 100;

			return true;
		}

		return false;
	}

	/**
	 * Restituisce se il tavolo è occupato.
	 * 
	 * @return se il tavolo è occupato.
	 */
	public boolean isOccupato() {
		return occupato;
	}

	/**
	 * Restituisce il piatto ordinato al tavolo.
	 * 
	 * @return il piatto ordinato al tavolo
	 */
	public Piatto getPiattoOrdinato() {
		return piattoOrdinato;
	}

	/**
	 * Restituisce la pazienza dei clienti al tavolo.
	 * 
	 * @return la pazienza dei clienti al tavolo
	 */
	public int getPazienza() {
		return pazienza;
	}

	/**
	 * Restituisce il numero del tavolo.
	 * 
	 * @return il numero del tavolo
	 */
	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	/**
	 * Imposta il numero del tavolo.
	 * 
	 * @param numeroTavolo il numero del tavolo da impostare
	 */
	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}

	/**
	 * Imposta il piatto ordinato al tavolo.
	 * 
	 * @param piattoOrdinato il piatto ordinato al tavolo da impostare
	 */
	public void setPiattoOrdinato(Piatto piattoOrdinato) {
		this.piattoOrdinato = piattoOrdinato;
	}

	/**
	 * Imposta la pazienza dei clienti al tavolo.
	 * 
	 * @param pazienza la pazienza dei clienti al tavolo da impostare
	 */
	public void setPazienza(int pazienza) {
		this.pazienza = pazienza;
	}

	/**
	 * Imposta se il tavolo è occupato o meno.
	 * 
	 * @param occupato se il tavolo è occupato o meno da impostare
	 */
	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}

	/**
	 * Serve il piatto al tavolo.
	 * 
	 * @param piatto il piatto da servire al tavolo
	 * @throws TavoloNonOccupatoException se il tavolo è vuoto, poiché non è possibile servire un piatto a un tavolo vuoto.
	 * @throws PiattoErratoException      se il piatto da servire è diverso da quello ordinato al tavolo
	 */
	public void serviTavolo(Piatto piatto) throws TavoloNonOccupatoException, PiattoErratoException {
		if (!occupato || piattoOrdinato.equals(Piatto.NESSUNO)) {
			throw new TavoloNonOccupatoException();
		}

		if (!piattoOrdinato.equals(piatto)) {
			throw new PiattoErratoException("Piatto errato. Il tavolo " + numeroTavolo + " ha ordinato " + piattoOrdinato + ".");
		}

		this.occupato = false;
		this.piattoOrdinato = Piatto.NESSUNO;
		this.pazienza = 100;
	}

	/**
	 * Resetta lo stato del tavolo, svuotando il tavolo e resettando la pazienza al massimo.
	 */
	public void reset() {
		this.occupato = false;
		this.piattoOrdinato = Piatto.NESSUNO;
		this.pazienza = 100;
	}

	/**
	 * Carica lo stato del tavolo da un altro oggetto tavolo salvato.
	 * 
	 * @param tavolo il tavolo da cui caricare lo stato
	 */
	public void caricaTavolo(Tavolo tavolo) {
		this.occupato = tavolo.occupato;
		this.piattoOrdinato = tavolo.piattoOrdinato;
		this.pazienza = tavolo.pazienza;
	}
}
