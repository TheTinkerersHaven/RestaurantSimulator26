package restaurantsim.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Oggetto che rappresenta la sala del ristorante, che contiene i tavoli e i piatti pronti.
 */
public class Sala {
	/**
	 * La lista dei piatti pronti, che contiene i piatti che sono stati preparati e sono pronti per essere serviti ai clienti.
	 * 
	 * L'accesso a questa lista non è thread-safe! Deve essere usato solo sull'EDT
	 */
	private ArrayList<Piatto> piattiPronti;
	/**
	 * La lista dei tavoli, che contiene lo stato di ogni tavolo.
	 * 
	 * L'accesso a questa lista è read-only, poiché i tavoli non vengono aggiunti o rimossi durante la partita. Lo stato di ogni tavolo viene modificato direttamente tramite i metodi del tavolo stesso, quindi non è necessario sincronizzare l'accesso alla lista dei tavoli.
	 */
	private ArrayList<Tavolo> tavoli;

	/**
	 * Inizializza la sala del ristorante, creando i tavoli e la lista dei piatti pronti.
	 */
	public Sala() {
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
	 */
	public void aggiungiPiatto(Piatto piatto) {
		piattiPronti.add(piatto);
	}

	/**
	 * Rimuove un piatto dalla lista dei piatti pronti.
	 * 
	 * @param index l'indice del piatto da rimuovere dalla lista dei piatti pronti
	 */
	public void rimuoviPiatto(int index) {
		piattiPronti.remove(index);
	}

	/**
	 * Resetta lo stato della sala, svuotando la lista dei piatti pronti e resettando lo stato di ogni tavolo.
	 * 
	 * @throws InterruptedException se il thread viene interrotto durante il reset dei tavoli.
	 */
	public void reset() throws InterruptedException {
		for (Tavolo tavolo : tavoli) {
			tavolo.reset();
		}

		piattiPronti.clear();
	}

	/**
	 * Imposta la lista dei piatti pronti.
	 * 
	 * @param piattiPronti la lista dei piatti pronti da impostare
	 */
	public void setPiattiPronti(ArrayList<Piatto> piattiPronti) {
		this.piattiPronti = piattiPronti;
	}

	/**
	 * Carica lo stato dei tavoli da una lista di tavoli salvati.
	 * 
	 * @param tavoli la lista dei tavoli da cui caricare lo stato
	 * @throws InterruptedException se il thread viene interrotto durante il caricamento dello stato dei tavoli.
	 */
	public void caricaTavoli(ArrayList<StatoTavolo> tavoli) throws InterruptedException {
		for (int i = 0; i < this.tavoli.size(); i++) {
			this.tavoli.get(i).caricaTavolo(tavoli.get(i));
		}
	}
}
