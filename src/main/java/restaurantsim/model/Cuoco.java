package restaurantsim.model;

/**
 * Oggetto che rappresenta un cuoco, con il piatto che sta preparando e il tempo rimanente per completarlo.
 */
public class Cuoco {
	/**
	 * Il piatto che il cuoco sta preparando. Se è NESSUNO, il cuoco è libero.
	 */
	private Piatto piattoInPreparazione;
	/**
	 * Il tempo rimanente per completare la preparazione del piatto. Se è 0, il cuoco è libero.
	 */
	private int tempoRimanente;

	/**
	 * Inizializza un cuoco libero, senza piatto in preparazione.
	 */
	public Cuoco() {
		piattoInPreparazione = Piatto.NESSUNO;
		tempoRimanente = 0;
	}

	/**
	 * Restituisce il piatto che il cuoco sta preparando. Se è NESSUNO, il cuoco è libero.
	 * 
	 * @return il piatto che il cuoco sta preparando, o NESSUNO se il cuoco è libero
	 */
	public Piatto getPiattoInPreparazione() {
		return piattoInPreparazione;
	}

	/**
	 * Restituisce il tempo rimanente per completare la preparazione del piatto.
	 * 
	 * @return il tempo rimanente per completare la preparazione del piatto
	 */
	public int getTempoRimanente() {
		return tempoRimanente;
	}

	/**
	 * Inizia la preparazione di un piatto, impostando il piatto in preparazione e il tempo rimanente in base al tempo di preparazione del piatto.
	 * 
	 * @param piatto il piatto da preparare
	 */
	public void iniziaPreparazione(Piatto piatto) {
		piattoInPreparazione = piatto;
		tempoRimanente = piatto.getTempoDiPreparazione();
	}

	/**
	 * Aggiorna lo stato del cuoco, decrementando il tempo rimanente se sta preparando un piatto.
	 */
	public void preparaPiatto() {
		if (piattoInPreparazione.equals(Piatto.NESSUNO))
			return;

		tempoRimanente--;
		if (tempoRimanente == 0)
			piattoInPreparazione = Piatto.NESSUNO;
	}

	/**
	 * Resetta lo stato del cuoco, liberandolo da qualsiasi piatto in preparazione e azzerando il tempo rimanente.
	 */
	public void reset() {
		piattoInPreparazione = Piatto.NESSUNO;
		tempoRimanente = 0;
	}

	/**
	 * Carica lo stato del cuoco da un altro oggetto cuoco salvato.
	 * 
	 * @param cuoco il cuoco da cui caricare lo stato
	 */
	public void caricaCuoco(Cuoco cuoco) {
		this.piattoInPreparazione = cuoco.piattoInPreparazione;
		this.tempoRimanente = cuoco.tempoRimanente;
	}
}
