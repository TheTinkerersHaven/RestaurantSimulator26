package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import restaurantsim.model.Sala;
import restaurantsim.view.MainPanel;
import restaurantsim.view.NotificaPanel;
import restaurantsim.view.Window;

public class ControllerNotifiche implements MouseListener, ActionListener {
	private Window window;
	private MainPanel mainPanel;
	private Sala sala;
	
	public ControllerNotifiche(Window window, Sala sala) {
		this.window = window;
		this.mainPanel = window.getPanel();
		this.sala = sala;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("del_all")) {
			sala.getNotifiche().clear();
			mainPanel.getSalaPanel().aggiornaNotifiche(sala.getNotifiche(), this);
			mainPanel.getCucinaPanel().aggiornaNotifiche(sala.getNotifiche(), this);
		} else {
			sala.getNotifiche().remove(Integer.parseInt(e.getActionCommand()));
			mainPanel.getSalaPanel().aggiornaNotifiche(sala.getNotifiche(), this);
			mainPanel.getCucinaPanel().aggiornaNotifiche(sala.getNotifiche(), this);
			window.setSize(mainPanel.cambiaPannello("cucina"));			
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		NotificaPanel notifPanel = (NotificaPanel) e.getComponent().getParent();
		switch(e.getComponent().getName()) {
			case "textAreaNotif":
				mainPanel.getOverlayUI().remove(notifPanel);
				mainPanel.getOverlayUI().revalidate();
				mainPanel.getOverlayUI().repaint();
				window.setSize(mainPanel.cambiaPannello("cucina"));
				break;
			case "lblCloseNotif":
				mainPanel.getOverlayUI().remove(notifPanel);
				mainPanel.getOverlayUI().revalidate();
				mainPanel.getOverlayUI().repaint();
				break;
			default:
				throw new IllegalStateException("Azione non eseguibile.");
		}
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
