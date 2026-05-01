package restaurantsim.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;

/**
 * Controller che gestisce la visualizzazione di un menu contestuale (popup) al click destro del mouse.
 */
public class PopupController implements MouseListener {
	/**
	 * Il menu contestuale da visualizzare al click destro del mouse.
	 */
	private JPopupMenu popup;

	/**
	 * Crea un PopupController associato al menu contestuale specificato.
	 *
	 * @param popup Il menu contestuale da visualizzare al click destro del mouse
	 */
	public PopupController(JPopupMenu popup) {
		this.popup = popup;
	}

	/**
	 * Mostra il menu contestuale al click destro del mouse. Come da documentazione di MouseEvent.isPopupTrigger(), su alcune piattaforme il menu contestuale viene attivato al rilascio del click invece che alla pressione.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.isPopupTrigger()) {
			showMenu(e);
		}
	}

	/**
	 * Mostra il menu contestuale al rilascio del click destro del mouse. Come da documentazione di MouseEvent.isPopupTrigger(), su alcune piattaforme il menu contestuale viene attivato al rilascio del click invece che alla pressione.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			showMenu(e);
		}
	}

	/**
	 * Mostra il menu contestuale alla posizione del mouse per il componente che ha generato l'evento.
	 */
	private void showMenu(MouseEvent e) {
		popup.show(e.getComponent(), e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Non usato.
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(MouseEvent e) {
		// Non usato.
	}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(MouseEvent e) {
		// Non usato.
	}
}
