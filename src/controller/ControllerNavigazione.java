package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainPanel;
import view.Window;

public class ControllerNavigazione implements ActionListener {
	private Window window;
	private MainPanel panel;
	
	public ControllerNavigazione(Window window) {
		this.window = window;
		this.panel = window.getPanel();
		panel.registraAscoltatoriMain(this);
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
		}
		
	}
	
}
