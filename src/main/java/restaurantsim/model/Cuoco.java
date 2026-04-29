package restaurantsim.model;

public class Cuoco implements Runnable {
	private Piatto piattoInPreparazione;
	private int tempoRimanente;

	public Cuoco() {
		piattoInPreparazione = Piatto.NESSUNO;
		tempoRimanente = 0;
	}

	public Piatto getPiattoInPreparazione() {
		return piattoInPreparazione;
	}

	public int getTempoRimanente() {
		return tempoRimanente;
	}

	public void iniziaPreparazione(Piatto piatto) {
		piattoInPreparazione = piatto;
		tempoRimanente = piatto.getTempoDiPreparazione();
	}

	@Override
	public void run() {
		if (piattoInPreparazione.equals(Piatto.NESSUNO))
			return;

		tempoRimanente--;
		if (tempoRimanente == 0)
			piattoInPreparazione = Piatto.NESSUNO;
	}

	public void reset() {
		piattoInPreparazione = Piatto.NESSUNO;
		tempoRimanente = 0;
	}

}
