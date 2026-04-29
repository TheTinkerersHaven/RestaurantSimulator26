package restaurantsim.main;

import java.awt.EventQueue;

import restaurantsim.controller.ControllerCuoco;
import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.controller.PiattoTransferHandle;
import restaurantsim.model.Sala;
import restaurantsim.view.MainPanel;
import restaurantsim.view.Window;

public class Main {
	private static void mainUI() {
		/// Model setup
		Sala sala = new Sala();

		/// View setup
		Window window = new Window();
		MainPanel mainPanel = window.getPanel();

		/// Controller setup
		ControllerNotifiche controllerNotifiche = new ControllerNotifiche(window, sala);

		@SuppressWarnings("unused")
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window);

		ControllerCuoco.registraAscoltatori(mainPanel, mainPanel.getSalaPanel(), sala, controllerNotifiche);
		PiattoTransferHandle.registraTrasnferHandles(sala, mainPanel.getSalaPanel());

		/// Debug
		// Per fare delle navigazioni iniziali ed evitare di cliccare ogni volta nei pulsanti della nagivazione per testare le funzionalità
		// controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "nuova_partita"));

		// Aggiungi piatti pronti per testare funzionalità del bancone
		// try {
		// sala.aggiungiPiatto(model.Piatto.SASHIMI);
		// sala.aggiungiPiatto(model.Piatto.URAMAKI_RAINBOW);
		// mainPanel.getSalaPanel().aggiornaBancone(sala.getPiattiPronti());
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// Aggiungi notifiche per testare funzionalità notifiche
		// String notif = "Notifica di esempio";
		// sala.registraNotifica(notif);
		// mainPanel.mostraNotifica(sala.getNotifiche(), notif, controllerNotifiche);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> mainUI());
	}
}
