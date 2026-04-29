package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.view.MainPanel;
import restaurantsim.view.NotificaPanel;
import restaurantsim.view.Window;

public class ControllerNotifiche implements MouseListener, ActionListener {
	public static final String ORIGINE_SALA = "sala";
	public static final String ORIGINE_CUCINA = "cucina";

	private Window window;
	private MainPanel mainPanel;
	private Gioco gioco;

	public ControllerNotifiche(Window window, Gioco gioco) {
		this.window = window;
		this.mainPanel = window.getPanel();
		this.gioco = gioco;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("del_all")) {
			gioco.getNotifiche().clear();
			aggiornaNotifiche();
		} else {
			int index = Integer.parseInt(e.getActionCommand());
			Notifica notif = gioco.getNotifiche().get(index);

			gioco.getNotifiche().remove(index);

			aggiornaNotifiche();
			window.setSize(mainPanel.cambiaPannello(notif.getOrigine()));
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		java.awt.Container parent = e.getComponent().getParent();
		if (!(parent instanceof NotificaPanel)) {
			// Se il genitore diretto non è NotificaPanel, provo a risalire la gerarchia
			while (parent != null && !(parent instanceof NotificaPanel)) {
				parent = parent.getParent();
			}
		}

		if (parent instanceof NotificaPanel) {
			NotificaPanel notifPanel = (NotificaPanel) parent;

			mainPanel.getOverlayUI().remove(notifPanel);
			mainPanel.getOverlayUI().revalidate();
			mainPanel.getOverlayUI().repaint();

			switch (e.getComponent().getName()) {
				case "textAreaNotif":
					window.setSize(mainPanel.cambiaPannello(notifPanel.getOrigine()));
					break;
				case "lblCloseNotif":
					// Non serve fare altro
					break;
				default:
					// Se il componente cliccato non è uno di quelli gestiti, non facciamo nulla invece di lanciare eccezione
					break;
			}
		}
	}

	public void aggiornaNotifiche() {
		mainPanel.aggiornaMenuNotifiche(gioco.getNotifiche(), this);
	}

	public void mostraNotifica(Notifica notifica) {
		NotificaPanel notif = mainPanel.mostraNotifica(notifica, this);
		mainPanel.aggiornaMenuNotifiche(gioco.getNotifiche(), this);

		Timer timer = new Timer(5000, event -> {
			mainPanel.getOverlayUI().remove(notif);
			mainPanel.getOverlayUI().repaint();
		});

		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Non usato.
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Non usato.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Non usato.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Non usato.
	}
}
