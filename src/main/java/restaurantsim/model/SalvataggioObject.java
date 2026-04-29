package restaurantsim.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class SalvataggioObject {
	// Qui ci vanno i dati ricevuti dal controller, sennò niente MVC
	private ArrayList<Piatto> piattiPronti;
	private LinkedList<String> notifiche;
	private ArrayList<Tavolo> tavoli;

	public SalvataggioObject() {
	}

	public SalvataggioObject(ArrayList<Piatto> piattiPronti, LinkedList<String> notifiche, ArrayList<Tavolo> tavoli) {
		this.piattiPronti = piattiPronti;
		this.notifiche = notifiche;
		this.tavoli = tavoli;
	}

}
