package restaurantsim.controller;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.model.StatoTavolo;
import restaurantsim.model.Tavolo;
import restaurantsim.model.TavoloOccupatoException;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.PannelloSala;

/**
 * Worker che gestisce l'arrivo dei clienti ai tavoli.
 */
public class ArrivoClientiWorker extends SwingWorker<Void, StatoTavolo> {
	/**
	 * Tempo minimo in millisecondi tra l'arrivo di un cliente e l'altro.
	 */
	private static final int TEMPO_MINIMO_ARRIVO = 8000;
	/**
	 * Tempo massimo in millisecondi tra l'arrivo di un cliente e l'altro.
	 */
	private static final int TEMPO_MASSIMO_ARRIVO = 15000;

	/**
	 * Gioco a cui appartiene il worker.
	 */
	private Gioco gioco;
	/**
	 * Interfaccia grafica della sala.
	 */
	private PannelloSala pannelloSala;
	/**
	 * Controller delle notifiche per mostrare le notifiche quando arrivano i clienti.
	 */
	private ControllerNotifiche controllerNotifiche;
	/**
	 * Controller della partita per gestire i timer dei tavoli quando arrivano i clienti.
	 */
	private ControllerPartita controllerPartita;
	/**
	 * Controller per gestire i suoni.
	 */
	private ControllerSuoni controllerSuoni;
	/**
	 * Generatore di numeri casuali per determinare i tempi di arrivo dei clienti.
	 */
	private Random random;

	/**
	 * Inizializza il worker
	 *
	 * @param gioco               Il gioco a cui appartiene il worker
	 * @param pannelloSala        L'interfaccia grafica della sala
	 * @param controllerNotifiche Il controller delle notifiche
	 * @param controllerPartita   Il controller della partita
	 * @param controllerSuoni     Il controller dei suoni
	 */
	public ArrivoClientiWorker(Gioco gioco, PannelloSala pannelloSala, ControllerNotifiche controllerNotifiche, ControllerPartita controllerPartita, ControllerSuoni controllerSuoni) {
		this.gioco = gioco;
		this.pannelloSala = pannelloSala;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerPartita = controllerPartita;
		this.controllerSuoni = controllerSuoni;
		this.random = new Random();
	}

	/**
	 * Aspetta il tempo randomico e cerca un tavolo libero. Se ne trova uno, fa arrivare i clienti e pubblica l'aggiornamento.
	 * 
	 * @return Nulla, il metodo è void. {@link java.lang.Void} è una classe non instanziabile usata per indicare che non viene restituito alcun valore.
	 */
	@Override
	public Void doInBackground() {
		try {
			// Usiamo isCancelled per sapere quando la partita è stata interrotta e fermare il ciclo
			while (!isCancelled()) {
				// random.nextInt usa il limite superiore esclusivo, quindi dobbiamo aggiungere 1 per includere TEMPO_MASSIMO_ARRIVO
				int attesa = random.nextInt(TEMPO_MINIMO_ARRIVO, TEMPO_MASSIMO_ARRIVO + 1);
				Thread.sleep(attesa);

				boolean tavoloLiberoTrovato = false;

				int i = 0;
				while (i < Gioco.NUM_TAVOLI && !tavoloLiberoTrovato) {
					Tavolo tavolo = gioco.getSala().getTavolo(i + 1);

					try {
						StatoTavolo statoTavolo = tavolo.prenotaTavoloSeLibero();

						tavoloLiberoTrovato = true;

						Timer timer = controllerPartita.getTimerTavolo(tavolo.getNumeroTavolo());
						timer.start();

						publish(statoTavolo);
						controllerSuoni.playClienteArrivato();
					} catch (TavoloOccupatoException e) {
						// Il tavolo è occupato, proviamo con il prossimo
					}

					i++;
				}
			}
		} catch (InterruptedException e) {
			// Non fare nulla, la partita è stata interrotta
		}

		return null;
	}

	/**
	 * Processa gli aggiornamenti pubblicati da doInBackground, aggiornando l'interfaccia e mostrando le notifiche.
	 *
	 * @param tavoliArrivati La lista degli stati dei tavoli a cui sono arrivati i clienti
	 */
	@Override
	protected void process(List<StatoTavolo> tavoliArrivati) {
		for (StatoTavolo tavolo : tavoliArrivati) {
			Notifica notifica = new Notifica("Tavolo " + tavolo.getNumeroTavolo() + " ha un nuovo cliente!", ControllerNotifiche.ORIGINE_SALA);

			gioco.registraNotifica(notifica);
			controllerNotifiche.mostraNotifica(notifica);

			PannelloTavolo pannelloTavolo = pannelloSala.getPannelloTavolo(tavolo.getNumeroTavolo());

			pannelloTavolo.aggiornaTavolo(tavolo);
		}
	}
}
