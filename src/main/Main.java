package main;

import controller.ControllerCuoco;
import controller.ControllerNavigazione;
import model.Sala;
import view.Window;

public class Main {
	public static void main(String[] args) {
		/// Model setup
		
		Sala sala = new Sala();
		
		/// View setup
		
		Window window = new Window();

		/// Controller setup
		
		// Facciamo suppress cosi si può scommentare la riga sotto per il debug
		@SuppressWarnings("unused")
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window);

		// Per debug: se vuoi aprire l'app in un altra schermata
		// controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "vai_cucina_da_sala"));
		
		ControllerCuoco.registraAscoltatori(window.getPanel(), window.getPanel().getSalaPanel(), sala);
	}
}
