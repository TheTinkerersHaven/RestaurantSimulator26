package restaurantsim.model;

import java.util.Random;

public class Tavolo {
	private int numeroTavolo;
	private Piatto piattoOrdinato;
	private int pazienza;
	private boolean occupato;
	private Random random = new Random();

	public Tavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
		this.piattoOrdinato = Piatto.NESSUNO;
		this.pazienza = 100;
		this.occupato = false;
	}

	public void faiArrivareClienti() throws Exception {
		if (occupato) {
			throw new Exception("Tavolo già occupato.");
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

	public boolean decrementaPazienza() throws Exception {
		if (!occupato) {
			throw new Exception("Tavolo vuoto.");
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

	public boolean isOccupato() {
		return occupato;
	}

	public Piatto getPiattoOrdinato() {
		return piattoOrdinato;
	}

	public int getPazienza() {
		return pazienza;
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void serviTavolo(Piatto piatto) throws Exception {
		if (!occupato || piattoOrdinato.equals(Piatto.NESSUNO)) {
			throw new Exception("Tavolo vuoto.");
		}

		if (!piattoOrdinato.equals(piatto)) {
			throw new Exception("Piatto errato. Il tavolo " + numeroTavolo + " ha ordinato " + piattoOrdinato + ".");
		}

		this.occupato = false;
		this.piattoOrdinato = Piatto.NESSUNO;
		this.pazienza = 100;
	}

	public void setNumeroTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;
	}

	public void setPiattoOrdinato(Piatto piattoOrdinato) {
		this.piattoOrdinato = piattoOrdinato;
	}

	public void setPazienza(int pazienza) {
		this.pazienza = pazienza;
	}

	public void setOccupato(boolean occupato) {
		this.occupato = occupato;
	}
}
