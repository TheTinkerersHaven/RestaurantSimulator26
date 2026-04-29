package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.view.MainPanel;
import restaurantsim.view.Window;

public class ControllerNavigazione implements ActionListener {
	private Window window;
	private MainPanel panel;
	
	public ControllerNavigazione(Window window) {
		this.window = window;
		this.panel = window.getPanel();
		panel.registraAscoltatoriNavigazioneMain(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case "classifica":
				window.setSize(panel.cambiaPannello("classifica"));
				break;
			case "nuova_partita":
				window.setSize(panel.cambiaPannello("sala"));
				break;
			case "indietro_classifica":
				window.setSize(panel.cambiaPannello("menu"));
				break;
			case "vai_cucina_da_sala":
				window.setSize(panel.cambiaPannello("cucina"));
				break;
			case "vai_sala_da_cucina":
				window.setSize(panel.cambiaPannello("sala"));
				break;
		}
		
	}
	
}
