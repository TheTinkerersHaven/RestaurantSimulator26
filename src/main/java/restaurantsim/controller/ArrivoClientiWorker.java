package restaurantsim.controller;

import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloTavolo;

public class ArrivoClientiWorker extends SwingWorker<Void, Integer> {
	private Gioco gioco;
	private MainPanel mainPanel;
	private ControllerNotifiche controllerNotifiche;
	private Random random;

	public ArrivoClientiWorker(Gioco gioco, MainPanel mainPanel, ControllerNotifiche controllerNotifiche) {
		this.gioco = gioco;
		this.mainPanel = mainPanel;
		this.controllerNotifiche = controllerNotifiche;
		this.random = new Random();
	}

	@Override
	public Void doInBackground() {
		try {
			while (!isCancelled()) {
				int attesa = 8000 + random.nextInt(7000); // da 8 a 15 secondi
				Thread.sleep(attesa);

				// Trova il primo tavolo non occupato
				Tavolo tavoloLibero = null;

				int i = 0;
				while (i < Sala.NUM_TAVOLI && tavoloLibero == null) {
					Tavolo t = gioco.getSala().getTavolo(i + 1);
					if (!t.isOccupato()) {
						tavoloLibero = t;
					}
					i++;
				}

				// Se c'è un tavolo libero, fai arrivare i clienti
				if (tavoloLibero != null) {
					try {
						tavoloLibero.faiArrivareClienti();

						// Publish chiamerà il metodo process() appena l'EDT sarà pronto a processare gli aggiornamenti
						publish(tavoloLibero.getNumeroTavolo());
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

	@Override
	protected void process(List<Integer> tavoliArrivati) {
		try {
			for (Integer numeroTavolo : tavoliArrivati) {
				Notifica notifica = new Notifica("Tavolo " + numeroTavolo + " ha un nuovo cliente!", ControllerNotifiche.ORIGINE_SALA);

				gioco.registraNotifica(notifica);
				controllerNotifiche.mostraNotifica(notifica);

				PannelloTavolo pannelloTavolo = mainPanel.getSalaPanel().getPannelloTavolo(tavoliArrivati.get(tavoliArrivati.size() - 1));

				pannelloTavolo.aggiornaTavolo(gioco.getSala().getTavolo(numeroTavolo));
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
	}
}
