package restaurantsim.view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

import restaurantsim.model.Tavolo;

import javax.swing.border.LineBorder;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class PannelloTavolo extends JPanel {
	private JLabel lblOrdinePiatto;
	private JProgressBar progressBar;
	private JPanel panel;
	private JLabel lblPazienza;
	private JPanel panelPiatto;
	private JPanel panelPazienza;
	private JLabel lblPiattoOrdinato;

	private int numeroTavolo;

	public PannelloTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;

		LineBorder lineBorder = new LineBorder(new Color(184, 207, 229));
		TitledBorder title = new TitledBorder(lineBorder, "Tavolo " + numeroTavolo, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0));
		setBorder(BorderFactory.createCompoundBorder(title, BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		panel = new JPanel();
		panel.setBackground(new Color(126, 83, 1));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		add(panel);

		panel.add(Box.createVerticalGlue());

		panelPiatto = new JPanel();
		panelPiatto.setMaximumSize(new Dimension(32767, 20));
		panelPiatto.setOpaque(false);
		panelPiatto.setVisible(false);
		panel.add(panelPiatto);

		lblOrdinePiatto = new JLabel("Piatto Ordinato:");
		lblOrdinePiatto.setForeground(Color.WHITE);
		panelPiatto.add(lblOrdinePiatto);

		lblPiattoOrdinato = new JLabel();
		lblPiattoOrdinato.setForeground(Color.WHITE);
		panelPiatto.add(lblPiattoOrdinato);

		panelPazienza = new JPanel();
		panelPazienza.setMaximumSize(new Dimension(32767, 20));
		panelPazienza.setOpaque(false);
		panelPazienza.setVisible(false);
		panel.add(panelPazienza);

		lblPazienza = new JLabel("Pazienza:");
		lblPazienza.setForeground(Color.WHITE);
		panelPazienza.add(lblPazienza);

		progressBar = new JProgressBar();
		panelPazienza.add(progressBar);
		progressBar.setForeground(new Color(255, 0, 0));
		progressBar.setStringPainted(true);

		panel.add(Box.createVerticalGlue());
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void aggiornaTavolo(Tavolo tavolo) {
		if (!tavolo.isOccupato()) {
			panelPiatto.setVisible(false);
			panelPazienza.setVisible(false);
			return;
		}

		panelPiatto.setVisible(true);
		panelPazienza.setVisible(true);

		progressBar.setValue(tavolo.getPazienza());
		lblPiattoOrdinato.setText(tavolo.getPiattoOrdinato().toString());
	}
}
