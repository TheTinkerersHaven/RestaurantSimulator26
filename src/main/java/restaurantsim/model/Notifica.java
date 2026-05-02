package restaurantsim.model;

/**
 * Oggetto che rappresenta una notifica da mostrare al giocatore
 */
public class Notifica {
	/**
	 * Il testo della notifica da mostrare al giocatore.
	 */
	private String testo;
	/**
	 * L'origine della notifica, che indica da quale parte del gioco è stata generata.
	 */
	private String origine;

	/**
	 * Inizializza una notifica vuota. Necessario per Jackson.
	 */
	public Notifica() {}

	/**
	 * Inizializza una notifica con il testo e l'origine specificati.
	 * 
	 * @param testo   Il testo della notifica da mostrare al giocatore.
	 * @param origine L'origine della notifica, che indica da quale parte del gioco è stata generata.
	 */
	public Notifica(String testo, String origine) {
		this.testo = testo;
		this.origine = origine;
	}

	/**
	 * Restituisce il testo della notifica da mostrare al giocatore.
	 * 
	 * @return il testo della notifica da mostrare al giocatore
	 */
	public String getTesto() {
		return testo;
	}

	/**
	 * Restituisce l'origine della notifica, che indica da quale parte del gioco è stata generata.
	 * 
	 * @return l'origine della notifica
	 */
	public String getOrigine() {
		return origine;
	}
}
