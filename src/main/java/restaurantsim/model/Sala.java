package restaurantsim.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Sala {
	private Semaphore mutexPiatti;
	private ArrayList<Piatto> piattiPronti;

	private Semaphore mutexNotifiche;
	private LinkedList<String> notifiche;

	private ArrayList<Tavolo> tavoli;

	public Sala() {
		mutexPiatti = new Semaphore(1);
		piattiPronti = new ArrayList<>();

		notifiche = new LinkedList<>();

		tavoli = new ArrayList<>(4);
		for (int i = 0; i < 4; i++) {
			tavoli.add(new Tavolo(i + 1));
		}
	}

	public List<Piatto> getPiattiPronti() {
		return piattiPronti;
	}

	public List<String> getNotifiche() {
		return notifiche;
	}

	public List<Tavolo> getTavoli() {
		return tavoli;
	}

	public Tavolo getTavolo(int numero) {
		return tavoli.get(numero - 1);
	}

	public void registraNotifica(String notif) throws InterruptedException {
		mutexNotifiche.acquire();

		notifiche.addFirst(notif);
		if (notifiche.size() == 11)
			notifiche.removeLast();

		mutexNotifiche.release();
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
}
