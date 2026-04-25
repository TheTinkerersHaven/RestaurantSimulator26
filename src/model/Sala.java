package model;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Sala {
	private ArrayList<Piatto> piattiPronti;
	private Semaphore mutex;
	
	public Sala() {
		piattiPronti = new ArrayList<>();
		mutex = new Semaphore(1);
	}

	public ArrayList<Piatto> getPiattiPronti() {
		return piattiPronti;
	}

	public void aggiungiPiatto(Piatto piatto) throws InterruptedException {
		mutex.acquire();

		piattiPronti.add(piatto);

		mutex.release();
	}
}
