package restaurantsim.main;

import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import restaurantsim.controller.ControllerCuoco;
import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.controller.PiattoTransferHandle;
import restaurantsim.controller.TimerTavolo;
import restaurantsim.model.Classifica;
import restaurantsim.model.Gioco;
import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloTavolo;
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

		/// Controller setup
		ControllerNotifiche controllerNotifiche = new ControllerNotifiche(window, gioco);
		ControllerNavigazione controllerNavigazione = new ControllerNavigazione(window, gioco, classifica, controllerNotifiche);

		ControllerCuoco.registraAscoltatori(mainPanel, salaPanel, gioco, controllerNotifiche);
		PiattoTransferHandle.registraTrasnferHandles(gioco, salaPanel, controllerNotifiche);

		for (int i = 1; i <= Sala.NUM_TAVOLI; i++) {
			Tavolo tavolo = gioco.getSala().getTavolo(i);
			PannelloTavolo pannelloTavolo = salaPanel.getPannelloTavolo(i);

			TimerTavolo timerTavolo = new TimerTavolo(tavolo, salaPanel, pannelloTavolo, gioco, controllerNotifiche, controllerNavigazione);
			Timer timer = new Timer(1000, timerTavolo);
			timer.start();
		}

		/// Debug
		// Per fare delle navigazioni iniziali ed evitare di cliccare ogni volta nei pulsanti della nagivazione per testare le funzionalità
		// controllerNavigazione.actionPerformed(new java.awt.event.ActionEvent(window, 0, "nuova_partita"));

		// Aggiungi piatti pronti per testare funzionalità del bancone
		// try {
		// sala.aggiungiPiatto(model.Piatto.SASHIMI);
		// sala.aggiungiPiatto(model.Piatto.URAMAKI_RAINBOW);
		// salaPanel.aggiornaBancone(sala.getPiattiPronti());
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
