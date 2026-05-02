package restaurantsim.controller;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Tavolo;
import restaurantsim.model.TavoloOccupatoException;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.PannelloSala;

/**
 * Worker che gestisce l'arrivo dei clienti ai tavoli.
 */
public class ArrivoClientiWorker extends SwingWorker<Void, Integer> {
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
	 */
	public ArrivoClientiWorker(Gioco gioco, PannelloSala pannelloSala, ControllerNotifiche controllerNotifiche, ControllerPartita controllerPartita) {
		this.gioco = gioco;
		this.pannelloSala = pannelloSala;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerPartita = controllerPartita;
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

				// Trova il primo tavolo non occupato
				Tavolo tavoloLibero = null;

				int i = 0;
				while (i < Gioco.NUM_TAVOLI && tavoloLibero == null) {
					Tavolo t = gioco.getSala().getTavolo(i + 1);
					if (!t.isOccupato()) {
						tavoloLibero = t;
					}
					i++;
				}

				// Se c'è un tavolo libero, fai arrivare i clienti
				if (tavoloLibero != null) {
					try {
						Timer timer = controllerPartita.getTimerTavolo(tavoloLibero.getNumeroTavolo());

						tavoloLibero.faiArrivareClienti();
						timer.start();

						// Publish chiamerà il metodo process() appena l'EDT sarà pronto a processare gli aggiornamenti
						publish(tavoloLibero.getNumeroTavolo());
					} catch (TavoloOccupatoException toe) {
						// Lascia che il timer riprovi il prossimo ciclo.
						// Questo non dovrebbe mai accadere per via dei controlli.
					} catch (Exception e) {
						System.out.println("Errore nell'arrivo dei clienti al tavolo " + tavoloLibero.getNumeroTavolo() + ": " + e.getMessage());

						// Se c'è un errore, passa al prossimo ciclo
					}
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
	 * @param tavoliArrivati La lista dei numeri dei tavoli a cui sono arrivati i clienti
	 */
	@Override
	protected void process(List<Integer> tavoliArrivati) {
		try {
			for (Integer numeroTavolo : tavoliArrivati) {
				Notifica notifica = new Notifica("Tavolo " + numeroTavolo + " ha un nuovo cliente!", ControllerNotifiche.ORIGINE_SALA);

				gioco.registraNotifica(notifica);
				controllerNotifiche.mostraNotifica(notifica);

				PannelloTavolo pannelloTavolo = pannelloSala.getPannelloTavolo(tavoliArrivati.get(tavoliArrivati.size() - 1));

				pannelloTavolo.aggiornaTavolo(gioco.getSala().getTavolo(numeroTavolo));
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}
