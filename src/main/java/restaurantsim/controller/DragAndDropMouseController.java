package restaurantsim.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * Controller che si occupa di gestire il drag and drop per compnenti che non lo supportano nativamente.
 */
public class DragAndDropMouseController implements MouseListener {
	/** {@inheritDoc} */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Non usato.
	}

	/**
	 * Quando il mouse viene premuto su un componente, viene avviata l'operazione di drag and drop per quel componente, se supportato.
	 *
	 * Se la sorgente dell'evento non è un componente, non viene fatto nulla in quanto non è possibile avviare un'operazione di drag and drop.
	 */
	@Override
	public void mousePressed(MouseEvent event) {
		Object source = event.getSource();

		if (!(source instanceof JComponent)) {
			return; // Se la sorgente non è un componente, non fare nulla
		}

		JComponent component = (JComponent) source;
		TransferHandler handler = component.getTransferHandler();
		handler.exportAsDrag(component, event, TransferHandler.MOVE);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(MouseEvent e) {
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
