package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.model.Gioco;
import restaurantsim.view.MainPanel;
import restaurantsim.view.Window;

public class ControllerNavigazione implements ActionListener {
	private Window window;
	private MainPanel panel;

	private ControllerPartita controllerPartita;
	private ControllerFinestra controllerFinestra;

	public ControllerNavigazione(Window window, Gioco gioco, ControllerPartita controllerPartita, ControllerFinestra controllerFinestra) {
		this.window = window;
		this.panel = window.getPanel();
		this.controllerPartita = controllerPartita;
		this.controllerFinestra = controllerFinestra;
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
				controllerPartita.nuovaParita();
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
			case "menu_torna_a_menu":
				controllerPartita.finisciPartita(ControllerPartita.ESCI_SOLO_NOME);
				cambiaMenu("menu");
				break;
			case "menu_esci":
				controllerFinestra.chiudiFinestraConConferma();
				break;
		}
	}

	public void cambiaMenu(String nome) {
		window.setSize(panel.cambiaPannello(nome));
	}
}
