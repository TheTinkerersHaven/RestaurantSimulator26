package restaurantsim.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import restaurantsim.model.StatoTavolo;

/**
 * Pannello che mostra un tavolo, con il piatto ordinato e la pazienza del cliente se il tavolo è occupato
 */
@SuppressWarnings("serial")
public class PannelloTavolo extends JPanel {
	/** Label per mostrare il testo "Piatto Ordinato: " prima del piatto ordinato */
	private JLabel lblOrdinePiatto;
	/** Barra di progresso per mostrare la pazienza del cliente al tavolo */
	private JProgressBar progressBar;
	/** Panel che contiene le informazioni del tavolo, come il piatto ordinato e la pazienza del cliente */
	private JPanel panel;
	/** Label per mostrare la pazienza del cliente al tavolo */
	private JLabel lblPazienza;
	/** Label per mostrare il piatto ordinato al tavolo */
	private JPanel panelPiatto;
	/** Label per mostrare il testo "Pazienza: " prima della pazienza del cliente */
	private JPanel panelPazienza;
	/** Label per mostrare il piatto ordinato al tavolo */
	private JLabel lblPiattoOrdinato;

	/**
	 * Numero del tavolo che è rappresentato da questo pannello
	 */
	private int numeroTavolo;

	/**
	 * Inizializza i componenti.
	 * 
	 * @param numeroTavolo il numero del tavolo che è rappresentato da questo pannello
	 */
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

	/**
	 * Restituisce il numero del tavolo rappresentato da questo pannello
	 * 
	 * @return il numero del tavolo rappresentato da questo pannello
	 */
	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	/**
	 * Aggiorna il pannello del tavolo con i dati dello stato fornito.
	 * Mostra o nasconde il piatto ordinato e la pazienza a seconda che il tavolo sia occupato o meno.
	 * 
	 * @param tavolo l'oggetto {@link StatoTavolo} contenente le informazioni aggiornate del tavolo.
	 */
	public void aggiornaTavolo(StatoTavolo tavolo) {
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
