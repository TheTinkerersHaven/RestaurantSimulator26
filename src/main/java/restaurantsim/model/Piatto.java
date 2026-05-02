package restaurantsim.model;

import java.net.URL;

/**
 * Rappresenta un piatto del menu del ristorante. Ogni piatto ha un tempo di preparazione specifico e un'immagine associata.
 */
public enum Piatto {
	/**
	 * Rappresenta l'assenza di un piatto. Utilizzato come valore predefinito o per indicare che non è stato selezionato alcun piatto.
	 */
	NESSUNO,
	/**
	 * Rappresenta il piatto di Sashimi.
	 */
	SASHIMI,
	/**
	 * Rappresenta il piatto di Uramaki Rainbow.
	 */
	URAMAKI_RAINBOW,
	/**
	 * Rappresenta il piatto di Hosomaki Maguro.
	 */
	HOSOMAKI_MAGURO;

	/**
	 * Restituisce il tempo di preparazione del piatto in minuti.
	 * 
	 * @return il tempo di preparazione del piatto in minuti
	 * @throws IllegalStateException se il piatto è NESSUNO o un valore non valido, poiché non è possibile calcolare il tempo di preparazione per un piatto non selezionato o invalido.
	 */
	public int getTempoDiPreparazione() {
		switch (this) {
			case SASHIMI:
				return 10;
			case URAMAKI_RAINBOW:
				return 30;
			case HOSOMAKI_MAGURO:
				return 20;
			default:
				throw new IllegalStateException("Impossibile calcolare il tempo di preparazione per un piatto invalido.");
		}
	}

	/**
	 * Restituisce l'URL dell'immagine associata al piatto.
	 * 
	 * @return l'URL dell'immagine del piatto
	 * @throws IllegalStateException se il piatto è NESSUNO o un valore non valido, poiché non è possibile recuperare l'immagine per un piatto non selezionato o invalido.
	 */
	public URL getImmaginePiatto() {
		switch (this) {
			case SASHIMI:
				return Piatto.class.getResource("/images/Sashimi.jpg");
			case URAMAKI_RAINBOW:
				return Piatto.class.getResource("/images/Rainbow.jpg");
			case HOSOMAKI_MAGURO:
				return Piatto.class.getResource("/images/Maguro.jpg");
			default:
				throw new IllegalStateException("Impossibile recuperare l'immagine per un piatto invalido.");
		}
	}

	/**
	 * Restituisce una rappresentazione testuale del piatto, utilizzando il nome del piatto invece del nome dell'enum.
	 * 
	 * @return una stringa che rappresenta il nome del piatto, o null se il piatto è nullo
	 */
	@Override
	public String toString() {
		switch (this) {
			case SASHIMI:
				return "Sashimi";
			case URAMAKI_RAINBOW:
				return "Uramaki Rainbow";
			case HOSOMAKI_MAGURO:
				return "Hosomaki Maguro";
			default:
				return null;
		}
	}
}
