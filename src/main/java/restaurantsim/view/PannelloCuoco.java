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

@SuppressWarnings("serial")
public class PannelloCuoco extends JPanel {
	private JLabel lblCuoco;
	private JProgressBar progressBar;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPiatto1;
	private JMenuItem mntmPiatto2;
	private JMenuItem mntmPiatto3;

	private int numeroCuoco;

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

	@Override
	public void setBounds(int x, int y, int width, int height) {
		// Controlla la dimensione minore per mantenere un quadrato
		int size = Math.min(width, height);
		// Trova l'angolo superiore sinistro per centrare il quadrato
		int nx = x + (width - size) / 2;
		int ny = y + (height - size) / 2;
		super.setBounds(nx, ny, size, size);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public int getNumeroCuoco() {
		return numeroCuoco;
	}

	public void aggiungiAscoltatori(ControllerCuoco al) {
		this.addMouseListener(new PopupController(popupMenu));

		mntmPiatto1.addActionListener(al);
		mntmPiatto2.addActionListener(al);
		mntmPiatto3.addActionListener(al);
	}

	public void mostraPiatto(URL immaginePiatto) {
		lblCuoco.setIcon(new ScaledImageIcon(immaginePiatto));
	}

	public void rimuoviImmagine() {
		lblCuoco.setIcon(null);
		progressBar.setValue(0);
	}

	public void aggiornaProgresso(int perc) {
		progressBar.setValue(perc);
	}
}