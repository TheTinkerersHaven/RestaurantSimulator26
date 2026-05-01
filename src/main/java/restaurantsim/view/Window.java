package restaurantsim.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Window extends JFrame implements WindowListener {
	private MainPanel panel;

	public Window() {
		panel = new MainPanel();
		setSize(600, 450);
		setContentPane(panel);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		chiudiFinestraConConferma();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// Non usato.

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// Non usato.

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// Non usato.

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// Non usato.

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// Non usato.

	}

	public MainPanel getPanel() {
		return panel;
	}

	public void chiudiFinestraConConferma() {
		int esito = JOptionPane.showConfirmDialog(this, "Sei sicuro di voler uscire?", "Conferma uscita", JOptionPane.YES_NO_OPTION);
		if (esito == JOptionPane.YES_OPTION)
			System.exit(0); 
	}
}
