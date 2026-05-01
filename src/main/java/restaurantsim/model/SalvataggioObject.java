package restaurantsim.model;

import java.util.ArrayList;
import java.util.LinkedList;

public class SalvataggioObject {
	// Qui ci vanno i dati ricevuti dal controller, sennò niente MVC
	private ArrayList<Piatto> piattiPronti;
	private LinkedList<String> notifiche;
	private ArrayList<Cuoco> cuochi;
	private ArrayList<Tavolo> tavoli;
	private ArrayList<String> classifica;

	public SalvataggioObject() {}

	public SalvataggioObject(ArrayList<Piatto> piattiPronti, LinkedList<String> notifiche, ArrayList<Cuoco> cuochi, ArrayList<Tavolo> tavoli, ArrayList<String> classifica) {
		this.piattiPronti = piattiPronti;
		this.notifiche = notifiche;
		this.cuochi = cuochi;
		this.tavoli = tavoli;
		this.classifica = classifica;
	}
}
