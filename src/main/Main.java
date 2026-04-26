package main;

import java.awt.EventQueue;

import controller.ControllerCuoco;
import controller.ControllerNavigazione;
import controller.ControllerNotifiche;
import model.Sala;
import view.Window;

public class Main {
	private static void mainUI() {
		/// Model setup
		Sala sala = new Sala();
		
		/// View setup
		Window window = new Window();

		/// Controller setup
		ControllerNotifiche controllerNotifiche = new ControllerNotifiche(window, sala);

		@SuppressWarnings("unused")
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window);

		// Per debug: se vuoi aprire l'app in un altra schermata
		controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "vai_cucina_da_sala"));

		ControllerCuoco.registraAscoltatori(window.getPanel(), window.getPanel().getSalaPanel(), sala, controllerNotifiche);

		/// Debug
		// se vuoi aprire l'app in un altra schermata
		controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "nuova_partita"));

		// aggiungi un piatto alla sala per testare
		try {
			sala.aggiungiPiatto(model.Piatto.SASHIMI);
			sala.aggiungiPiatto(model.Piatto.URAMAKI_RAINBOW);
			window.getPanel().getSalaPanel().aggiornaBancone(sala.getPiattiPronti());
		} catch (Exception e) {
			e.printStackTrace();
		}

		sala.registraNotifica("CIAO");
		window.getPanel().mostraNotifica(sala.getNotifiche(), null, controllerNotifiche);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> mainUI());
	}
}
