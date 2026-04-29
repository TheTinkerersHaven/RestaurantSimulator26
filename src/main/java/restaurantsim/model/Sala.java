package restaurantsim.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Sala {
	public static final int NUM_TAVOLI = 4;

	private Semaphore mutexPiatti;
	private ArrayList<Piatto> piattiPronti;

	private ArrayList<Tavolo> tavoli;

	public Sala() {
		mutexPiatti = new Semaphore(1);
		piattiPronti = new ArrayList<>();

		tavoli = new ArrayList<>(NUM_TAVOLI);
		for (int i = 0; i < NUM_TAVOLI; i++) {
			tavoli.add(new Tavolo(i + 1));
		}
	}

	public List<Piatto> getPiattiPronti() {
		return piattiPronti;
	}

	public List<Tavolo> getTavoli() {
		return tavoli;
	}

	public Tavolo getTavolo(int numero) {
		return tavoli.get(numero - 1);
	}

	public void aggiungiPiatto(Piatto piatto) throws InterruptedException {
		mutexPiatti.acquire();

		piattiPronti.add(piatto);

		mutexPiatti.release();
	}

	public void rimuoviPiatto(int index) throws InterruptedException {
		mutexPiatti.acquire();

		piattiPronti.remove(index);

		mutexPiatti.release();
	}

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
