package main;

import controller.ControllerCuoco;
import controller.ControllerNavigazione;
import view.Window;

public class Main {
	public static void main(String[] args) {
		Window window = new Window();

		// Facciamo suppress cosi si può scommentare la riga sotto per il debug
		@SuppressWarnings("unused")
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window);

		// Per debug: se vuoi aprire l'app in un altra schermata
		// controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "vai_cucina_da_sala"));
		
		ControllerCuoco.registraAscoltatori(window.getPanel());
	}
}
