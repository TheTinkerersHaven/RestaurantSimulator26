package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Sala {
	private ArrayList<Piatto> piattiPronti;
	private LinkedList<String> notifiche;
	private Semaphore mutex;
	
	public Sala() {
		piattiPronti = new ArrayList<>();
		notifiche = new LinkedList<>();
		mutex = new Semaphore(1);
	}

	public List<Piatto> getPiattiPronti() {
		return piattiPronti;
	}
	
	public List<String> getNotifiche() {
		return notifiche;
	}
	
	public void registraNotifica(String notif) {
		notifiche.addFirst(notif);
		if(notifiche.size() == 11) notifiche.removeLast();
	}

	public void aggiungiPiatto(Piatto piatto) throws InterruptedException {
		mutex.acquire();

		piattiPronti.add(piatto);

		mutex.release();
	}
}
