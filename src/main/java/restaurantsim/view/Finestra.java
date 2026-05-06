package restaurantsim.view;

import javax.swing.JFrame;

/**
 * La finestra del gioco.
 */
public class Finestra extends JFrame {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	/** Il pannello principale della finestra, che contiene tutti gli altri pannelli */
	private PannelloPrincipale pannelloPrincipale;

	/** Inizializza la finistra e mostrala */
	public Finestra() {
		pannelloPrincipale = new PannelloPrincipale();
		setSize(600, 450);
		setContentPane(pannelloPrincipale);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	/**
	 * Restituisce il pannello principale della finestra
	 * 
	 * @return il pannello principale della finestra
	 */
	public PannelloPrincipale getPanelloPrincipale() {
		return pannelloPrincipale;
	}
}
