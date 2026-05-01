package restaurantsim.main;

import java.awt.EventQueue;

import restaurantsim.controller.ControllerCuoco;
import restaurantsim.controller.ControllerFinestra;
import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.controller.ControllerPartita;
import restaurantsim.controller.PiattoTransferHandle;
import restaurantsim.model.Classifica;
import restaurantsim.model.Gioco;
import restaurantsim.view.PannelloCucina;
import restaurantsim.view.PannelloPrincipale;
import restaurantsim.view.PannelloSala;
import restaurantsim.view.Finestra;

/**
 * Classe principale del gioco, che si occupa di inizializzare lo stato del gioco, la view e i controller.
 */
public class Main {
	/**
	 * Inizializza lo stato del gioco, il gioco e la logica di gioco
	 */
	private static void init() {
		/// Model setup
		Gioco gioco = new Gioco();
		Classifica classifica = new Classifica();

		/// View setup
		Finestra window = new Finestra();
		PannelloPrincipale mainPanel = window.getPanelloPrincipale();
		PannelloSala salaPanel = mainPanel.getSalaPanel();
		PannelloCucina cucinaPanel = mainPanel.getCucinaPanel();

		/// Controller setup
		ControllerNotifiche controllerNotifiche = new ControllerNotifiche(gioco, mainPanel);
		ControllerPartita controllerPartita = new ControllerPartita(mainPanel, gioco, classifica, controllerNotifiche);

		ControllerFinestra controllerFinestra = new ControllerFinestra(window, controllerPartita);
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(gioco, window, controllerPartita, controllerFinestra);

		controllerNotifiche.setControllerNavigazione(controllerNavigazione);

		controllerPartita.initializzaTimerTavoli(salaPanel, controllerNavigazione);
		controllerPartita.initializzaTimerCuochi(salaPanel, cucinaPanel);

		ControllerCuoco.registraAscoltatori(gioco, cucinaPanel, controllerPartita);
		PiattoTransferHandle.registraTransferHandles(gioco, salaPanel, controllerNotifiche, controllerPartita);
	}

	/**
	 * Java Entrypoint
	 *
	 * @param args - Argomenti dalla linea di comando
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> init());
	}
}
