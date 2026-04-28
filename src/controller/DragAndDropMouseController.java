package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class DragAndDropMouseController implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        // Non usato.
    }

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
