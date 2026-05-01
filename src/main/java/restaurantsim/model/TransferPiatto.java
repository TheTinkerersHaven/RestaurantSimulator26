package restaurantsim.model;

/**
 * Classe di trasferimento dati per il piatto da esportare nell'operazione di Drag and Drop (DnD).
 */
public class TransferPiatto {
	/**
	 * Il piatto da esportare.
	 */
	private Piatto piatto;
	/**
	 * L'indice del piatto nella lista dei piatti pronti, utilizzato per identificare il piatto da rimuovere quando viene servito al cliente.
	 */
	private int indexPiatto;

	/**
	 * Inizializza i dati del piatto da esportare.
	 * 
	 * @param piatto      il piatto da esportare
	 * @param indexPiatto l'indice del piatto nella lista dei piatti
	 */
	public TransferPiatto(Piatto piatto, int indexPiatto) {
		this.piatto = piatto;
		this.indexPiatto = indexPiatto;
	}

	/**
	 * Restituisce il piatto da esportare.
	 * 
	 * @return il piatto da esportare
	 */
	public Piatto getPiatto() {
		return piatto;
	}

	/**
	 * Restituisce l'indice del piatto nella lista dei piatti pronti.
	 * 
	 * @return l'indice del piatto nella lista dei piatti pronti
	 */
	public int getIndexPiatto() {
		return indexPiatto;
	}
}
