package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import restaurantsim.model.Classifica;
import restaurantsim.model.Gioco;
import restaurantsim.model.Sala;
import restaurantsim.view.ClassificaPanel;
import restaurantsim.view.CucinaPanel;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.SalaPanel;
import restaurantsim.view.Window;

public class ControllerNavigazione implements ActionListener {
	private Window window;
	private MainPanel panel;

	private Gioco gioco;
	private Classifica classifica;
	private ControllerNotifiche controllerNavigazione;

	private ArrivoClientiWorker arrivoClientiWorker;

	public ControllerNavigazione(Window window, Gioco gioco, Classifica classifica, ControllerNotifiche controllerNavigazione) {
		this.window = window;
		this.panel = window.getPanel();
		this.gioco = gioco;
		this.classifica = classifica;
		this.controllerNavigazione = controllerNavigazione;
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
		arrivoClientiWorker = new ArrivoClientiWorker(gioco, panel, controllerNavigazione);
		arrivoClientiWorker.execute();
	}

	public void finisciPartita(boolean sconfitta) {
		arrivoClientiWorker.cancel(true);

		if(sconfitta) {
			ClassificaPanel classificaPanel = panel.getClassificaPanel(); 
			JOptionPane.showMessageDialog(window, "Gioco terminato! Hai fatto arrabbiare troppi clienti!", "Partita finita", JOptionPane.INFORMATION_MESSAGE);
			String nomeGiocatore;
			do {
				nomeGiocatore = JOptionPane.showInputDialog(window, "Inserisci il nome del giocatore.", "Inserisci nome", JOptionPane.INFORMATION_MESSAGE);
				if(nomeGiocatore == null || nomeGiocatore.isBlank()) JOptionPane.showMessageDialog(window, "Non puoi non inserire un nome.", "Errore", JOptionPane.OK_OPTION);
			} while (nomeGiocatore == null || nomeGiocatore.isBlank());
			classifica.inserisciPartita(nomeGiocatore, gioco.getPunteggio());
			classificaPanel.aggiornaClassifica(classifica.getClassifica());
		}

		/// Model reset
		gioco.reset();

		/// View reset
		// Notifiche
		panel.aggiornaMenuNotifiche(gioco.getNotifiche(), controllerNavigazione);

		// Sala
		SalaPanel salaPanel = panel.getSalaPanel();
		salaPanel.aggiornaPunteggio(gioco.getPunteggio());
		salaPanel.aggiornaBancone(gioco.getSala().getPiattiPronti());
		for (int i = 1; i <= Sala.NUM_TAVOLI; i++) {
			salaPanel.getPannelloTavolo(i).aggiornaTavolo(gioco.getSala().getTavolo(i));
		}

		// Cucina
		CucinaPanel cucinaPanel = panel.getCucinaPanel();
		for (int i = 1; i <= Gioco.NUM_CUOCHI; i++) {
			PannelloCuoco pannelloCuoco = cucinaPanel.getPannelloCuoco(i);
			pannelloCuoco.aggiornaProgresso(0);
			pannelloCuoco.rimuoviImmagine();
		}
		
		cambiaMenu("menu");
	}

	private void cambiaMenu(String nome) {
		window.setSize(panel.cambiaPannello(nome));
	}
}
