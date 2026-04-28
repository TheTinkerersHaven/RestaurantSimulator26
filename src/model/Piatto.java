package model;

import java.net.URL;

public enum Piatto {
	NESSUNO,
 	SASHIMI,
 	URAMAKI_RAINBOW,
 	HOSOMAKI_MAGURO;
	
	public int getTempoDiPreparazione() {
		switch (this) {
			case SASHIMI:
				return 2;
			case URAMAKI_RAINBOW:
				return 180;
			case HOSOMAKI_MAGURO:
				return 120;
			default:
				throw new IllegalStateException("Impossibile calcolare il tempo di preparazione per un piatto invalido.");
		}
	}
	
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
				throw new IllegalStateException("Impossibile recuperare il nome per un piatto invalido.");
		}
	}
}
