package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import restaurantsim.model.Gioco;
import restaurantsim.model.Sala;
import restaurantsim.view.CucinaPanel;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.SalaPanel;
import restaurantsim.view.Window;

public class ControllerNavigazione implements ActionListener {
	private Window window;
	private MainPanel panel;

	private Gioco gioco;
	private ControllerNotifiche controllerNavigatione;

	ArrivoClientiWorker arrivoClientiWorker;

	public ControllerNavigazione(Window window, Gioco gioco, ControllerNotifiche controllerNavigatione) {
		this.window = window;
		this.panel = window.getPanel();
		this.gioco = gioco;
		this.controllerNavigatione = controllerNavigatione;
		panel.registraAscoltatoriNavigazioneMain(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case "classifica":
				cambiaMenu("classifica");
				break;
			case "nuova_partita":
				cambiaMenu("sala");
				nuovaParita();
				break;
			case "indietro_classifica":
				cambiaMenu("menu");
				break;
			case "vai_cucina_da_sala":
				cambiaMenu("cucina");
				break;
			case "vai_sala_da_cucina":
				cambiaMenu("sala");
				break;
		}
	}

	private void nuovaParita() {
		arrivoClientiWorker = new ArrivoClientiWorker(gioco, panel, controllerNavigatione);
		arrivoClientiWorker.execute();
	}

	public void finisciPartita() {
		arrivoClientiWorker.cancel(true);

		/// Model reset
		gioco.reset();

		/// View reset
		// Notifiche
		panel.aggiornaMenuNotifiche(gioco.getNotifiche(), controllerNavigatione);

		// Sala
		SalaPanel salaPanel = panel.getSalaPanel();
		salaPanel.aggiornaPunteggio(gioco.getPunteggio());
		salaPanel.aggiornaBancone(gioco.getSala().getPiattiPronti());
		for (int i = 0; i < Sala.NUM_TAVOLI; i++) {
			salaPanel.getPannelloTavolo(i + 1).aggiornaTavolo(gioco.getSala().getTavolo(i + 1));
		}

		// Cucina
		CucinaPanel cucinaPanel = panel.getCucinaPanel();
		for (int i = 0; i < Gioco.NUM_CUOCHI; i++) {
			PannelloCuoco pannelloCuoco = cucinaPanel.getPannelloCuoco(i + 1);
			pannelloCuoco.aggiornaProgresso(0);
			pannelloCuoco.rimuoviImmagine();
		}

		JOptionPane.showMessageDialog(window, "Gioco terminato! Hai fatto arrabbiare troppi client!", "Partita finita", JOptionPane.INFORMATION_MESSAGE);

		// TODO: Salva punteggio in classifica

		cambiaMenu("menu");
	}

	private void cambiaMenu(String nome) {
		window.setSize(panel.cambiaPannello(nome));
	}
}
