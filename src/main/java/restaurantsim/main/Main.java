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
import restaurantsim.view.CucinaPanel;
import restaurantsim.view.MainPanel;
import restaurantsim.view.SalaPanel;
import restaurantsim.view.Window;

public class Main {
	private static void mainUI() {
		/// Model setup
		Gioco gioco = new Gioco();
		Classifica classifica = new Classifica();

		/// View setup
		Window window = new Window();
		MainPanel mainPanel = window.getPanel();
		SalaPanel salaPanel = mainPanel.getSalaPanel();
		CucinaPanel cucinaPanel = mainPanel.getCucinaPanel();

		/// Controller setup
		ControllerNotifiche controllerNotifiche = new ControllerNotifiche(window, gioco);
		ControllerPartita controllerPartita = new ControllerPartita(mainPanel, gioco, classifica, controllerNotifiche);

		ControllerFinestra controllerFinestra = new ControllerFinestra(window);
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window, gioco, controllerPartita, controllerFinestra);

		controllerPartita.initializzaTimerTavoli(salaPanel, controllerNavigazione);
		controllerPartita.initializzaTimerCuochi(salaPanel, cucinaPanel);

		ControllerCuoco.registraAscoltatori(cucinaPanel, gioco, controllerPartita);
		PiattoTransferHandle.registraTransferHandles(gioco, salaPanel, controllerNotifiche);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> mainUI());
	}
}
