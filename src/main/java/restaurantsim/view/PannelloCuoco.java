package restaurantsim.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import restaurantsim.controller.ControllerCuoco;
import restaurantsim.controller.PopupController;

/**
 * Pannello che mostra un cuoco, con la sua immagine, il suo progresso e un menu popup per scegliere quale piatto preparare
 */
@SuppressWarnings("serial")
public class PannelloCuoco extends JPanel {
	/** Label per mostrare l'immagine del piatto che il cuoco sta preparando */
	private JLabel lblCuoco;
	/** Barra di progresso per mostrare il progresso del cuoco nella preparazione del piatto */
	private JProgressBar progressBar;
	/** Menu popup per scegliere quale piatto preparare */
	private JPopupMenu popupMenu;
	/** MenuItem per il piatto 1 */
	private JMenuItem mntmPiatto1;
	/** MenuItem per il piatto 2 */
	private JMenuItem mntmPiatto2;
	/** MenuItem per il piatto 3 */
	private JMenuItem mntmPiatto3;

	/** Numero del cuoco che è rappresentato da questo pannello */
	private int numeroCuoco;

	/**
	 * Inizializza i componenti.
	 * 
	 * @param numeroCuoco il numero del cuoco che è rappresentato da questo pannello
	 */
	public PannelloCuoco(int numeroCuoco) {
		this.numeroCuoco = numeroCuoco;

		setLayout(new BorderLayout(5, 5));
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229), 2, false), "Cuoco " + numeroCuoco, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));

		lblCuoco = new JLabel();
		lblCuoco.setOpaque(true);
		add(lblCuoco, BorderLayout.CENTER);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		add(progressBar, BorderLayout.SOUTH);

		popupMenu = new JPopupMenu();

		mntmPiatto1 = new JMenuItem("Sashimi");
		mntmPiatto1.setActionCommand("sashimi");
		popupMenu.add(mntmPiatto1);

		mntmPiatto2 = new JMenuItem("Uramaki Rainbow");
		mntmPiatto2.setActionCommand("uramakiRainbow");
		popupMenu.add(mntmPiatto2);

		mntmPiatto3 = new JMenuItem("Hosomaki Maguro");
		mntmPiatto3.setActionCommand("hosomakiMaguro");
		popupMenu.add(mntmPiatto3);
	}

	/**
	 * Sovrascrive setBounds per mantenere sempre una forma quadrata, indipendentemente dalle dimensioni che vengono date al pannello In questo modo anche l'immagine del cuoco rimane sempre quadrata
	 */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		// Controlla la dimensione minore per mantenere un quadrato
		int size = Math.min(width, height);
		// Trova l'angolo superiore sinistro per centrare il quadrato
		int nx = x + (width - size) / 2;
		int ny = y + (height - size) / 2;
		super.setBounds(nx, ny, size, size);
	}

	/**
	 * Sovrascrive getMaximumSize per permettere al layout di ridimensionare questo pannello in modo da occupare tutto lo spazio disponibile, mantenendo però la forma quadrata grazie a setBounds
	 */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Restituisce il numero del cuoco che è rappresentato da questo pannello
	 * 
	 * @return il numero del cuoco che è rappresentato da questo pannello
	 */
	public int getNumeroCuoco() {
		return numeroCuoco;
	}

	/**
	 * Registra gli ascoltatori per i componenti di questo pannello
	 * 
	 * @param controllerCuoco il controller dei cuochi da registrare come ascoltatore per i componenti di questo pannello
	 */
	public void aggiungiAscoltatori(ControllerCuoco controllerCuoco) {
		this.addMouseListener(new PopupController(popupMenu));

		mntmPiatto1.addActionListener(controllerCuoco);
		mntmPiatto2.addActionListener(controllerCuoco);
		mntmPiatto3.addActionListener(controllerCuoco);
	}

	/**
	 * Mostra l'immagine del piatto che il cuoco sta preparando, scalata per occupare tutto lo spazio disponibile nel pannello
	 * 
	 * @param immaginePiatto l'immagine del piatto da mostrare nel pannello
	 */
	public void mostraPiatto(URL immaginePiatto) {
		lblCuoco.setIcon(new ScaledImageIcon(immaginePiatto));
	}

	/**
	 * Rimuove l'immagine del piatto, mostrando solo il pannello vuoto con la barra di progresso sotto
	 */
	public void rimuoviImmagine() {
		lblCuoco.setIcon(null);
		progressBar.setValue(0);
	}

	/**
	 * Aggiorna il progresso del cuoco, mostrando la percentuale di completamento del piatto che sta preparando
	 * 
	 * @param percentuale la percentuale di completamento del piatto che il cuoco sta preparando, da 0 a 100
	 */
	public void aggiornaProgresso(int percentuale) {
		progressBar.setValue(percentuale);
	}
}
