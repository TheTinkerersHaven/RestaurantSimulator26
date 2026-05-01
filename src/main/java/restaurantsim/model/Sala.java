package restaurantsim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Oggetto che rappresenta la sala del ristorante, che contiene i tavoli e i piatti pronti.
 */
public class Sala {
	/**
	 * Il semaforo per sincronizzare l'accesso alla lista dei piatti pronti, che contiene i piatti che sono stati preparati.
	 */
	private Semaphore mutexPiatti;
	/**
	 * La lista dei piatti pronti, che contiene i piatti che sono stati preparati e sono pronti per essere serviti ai clienti.
	 * 
	 * L'accesso a questa lista è sincronizzato tramite il semaforo {@link #mutexPiatti}.
	 */
	private ArrayList<Piatto> piattiPronti;
	/**
	 * La lista dei tavoli, che contiene lo stato di ogni tavolo.
	 */
	private ArrayList<Tavolo> tavoli;

	/**
	 * Inizializza la sala del ristorante, creando i tavoli e la lista dei piatti pronti.
	 */
	public Sala() {
		mutexPiatti = new Semaphore(1);
		piattiPronti = new ArrayList<>();

		tavoli = new ArrayList<>(Gioco.NUM_TAVOLI);
		for (int i = 0; i < Gioco.NUM_TAVOLI; i++) {
			tavoli.add(new Tavolo(i + 1));
		}
	}

	/**
	 * Restituisce la lista dei piatti pronti.
	 * 
	 * @return la lista dei piatti pronti
	 */
	public List<Piatto> getPiattiPronti() {
		return piattiPronti;
	}

	/**
	 * Restituisce la lista dei tavoli.
	 * 
	 * @return la lista dei tavoli
	 */
	public List<Tavolo> getTavoli() {
		return tavoli;
	}

	/**
	 * Restituisce il tavolo con il numero specificato.
	 * 
	 * @param numero il numero del tavolo da restituire (da 1 a {@link Gioco#NUM_TAVOLI})
	 * @return il tavolo con il numero specificato
	 */
	public Tavolo getTavolo(int numero) {
		return tavoli.get(numero - 1);
	}

	/**
	 * Aggiunge un piatto alla lista dei piatti pronti.
	 * 
	 * @param piatto il piatto da aggiungere alla lista dei piatti pronti
	 * @throws InterruptedException se il thread viene interrotto mentre si aggiunge il piatto.
	 */
	public void aggiungiPiatto(Piatto piatto) throws InterruptedException {
		mutexPiatti.acquire();

		piattiPronti.add(piatto);

		mutexPiatti.release();
	}

	/**
	 * Rimuove un piatto dalla lista dei piatti pronti.
	 * 
	 * @param index l'indice del piatto da rimuovere dalla lista dei piatti pronti
	 * @throws InterruptedException se il thread viene interrotto mentre si rimuove il piatto.
	 */
	public void rimuoviPiatto(int index) throws InterruptedException {
		mutexPiatti.acquire();

		piattiPronti.remove(index);

		mutexPiatti.release();
	}

	/**
	 * Resetta lo stato della sala, svuotando la lista dei piatti pronti e resettando lo stato di ogni tavolo.
	 */
	public void reset() {
		// Aspettiamo eventuali operazioni
		mutexPiatti.acquireUninterruptibly();

		for (Tavolo tavolo : tavoli) {
			tavolo.reset();
		}

		piattiPronti.clear();

		mutexPiatti.release();
	}
}
