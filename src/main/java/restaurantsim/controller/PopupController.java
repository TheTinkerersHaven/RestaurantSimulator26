package restaurantsim.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

public class PopupController implements MouseListener {
	private JPopupMenu popup;

	public PopupController(JPopupMenu popup) {
		this.popup = popup;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			showMenu(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			showMenu(e);
		}
	}

	private void showMenu(MouseEvent e) {
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
