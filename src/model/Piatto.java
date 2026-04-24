package model;

import java.net.URL;

public enum Piatto {
	NESSUNO,
 	SASHIMI,
 	URAMAKI_RAINBOW,
 	HOSOMAKI_MAGURO;
	
	public int getTempoDiPreparazione() throws Exception {
		switch (this) {
			case SASHIMI:
				return 120;
			case URAMAKI_RAINBOW:
				return 180;
			case HOSOMAKI_MAGURO:
				return 120;
			default:
				throw new Exception("Impossibile calcolare il tempo di preparzione per un piatto invalido.");
		}
	}
	
	public URL getImmaginePiatto() throws Exception {
		switch (this) {
			case SASHIMI:
				return Piatto.class.getResource("/images/Sashimi.jpg");
			case URAMAKI_RAINBOW:
				return Piatto.class.getResource("/images/Rainbow.jpg");
			case HOSOMAKI_MAGURO:
				return Piatto.class.getResource("/images/Maguro.jpg");
			default:
				throw new Exception("Impossibile recuperare l'immagine per un piatto invalido.");
		}
	}
}
