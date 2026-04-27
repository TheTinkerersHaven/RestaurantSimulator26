package main;

import java.awt.EventQueue;

import controller.ControllerCuoco;
import controller.ControllerNavigazione;
import controller.ControllerNotifiche;
import model.Sala;
import view.MainPanel;
import view.Window;

public class Main {
	private static void mainUI() {
		/// Model setup
		
		Sala sala = new Sala();
		
		/// View setup
		
		Window window = new Window();
		MainPanel mp = window.getPanel();

		/// Controller setup
		
		// Facciamo suppress cosi si può scommentare la riga sotto per il debug
		@SuppressWarnings("unused")
		ControllerNotifiche controllerNotifiche = new ControllerNotifiche(window, sala);
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window);

		// Per debug: se vuoi aprire l'app in un altra schermata
		// controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "vai_cucina_da_sala"));
		
		ControllerCuoco.registraAscoltatori(window.getPanel(), window.getPanel().getSalaPanel(), sala, controllerNotifiche);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> mainUI());
	}
}
