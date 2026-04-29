package restaurantsim.view;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.border.TitledBorder;

import restaurantsim.controller.ControllerTavolo;
import restaurantsim.model.Tavolo;

import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class PannelloTavolo extends JPanel {
	private Component verticalGlue;
	private JLabel lblPresenzaClienti;
	private JLabel lblOrdinePiatti;
	private JProgressBar progressBar;
	private Component verticalGlue_1;
	private JPanel panel;
	private JPopupMenu popupMenu;
	private JMenuItem mntmServi;

	private int numeroTavolo;

	public PannelloTavolo(int numeroTavolo) {
		this.numeroTavolo = numeroTavolo;

		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Tavolo " + numeroTavolo, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		panel = new JPanel();
		add(panel);
		panel.setBackground(new Color(126, 83, 1));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		verticalGlue_1 = Box.createVerticalGlue();
		panel.add(verticalGlue_1);

		lblPresenzaClienti = new JLabel("Numero Clienti:");
		panel.add(lblPresenzaClienti);
		lblPresenzaClienti.setForeground(new Color(255, 255, 255));
		lblPresenzaClienti.setFont(new Font("Arial", Font.PLAIN, 15));

		lblOrdinePiatti = new JLabel("Piatti Ordinati:");
		panel.add(lblOrdinePiatti);
		lblOrdinePiatti.setForeground(new Color(255, 255, 255));
		lblOrdinePiatti.setFont(new Font("Arial", Font.PLAIN, 15));

		progressBar = new JProgressBar();
		panel.add(progressBar);
		progressBar.setForeground(new Color(255, 0, 0));
		progressBar.setStringPainted(true);
		progressBar.setValue(50);

		verticalGlue = Box.createVerticalGlue();
		panel.add(verticalGlue);
	}

	public int getNumeroTavolo() {
		return numeroTavolo;
	}

	public void aggiornaTavolo(Tavolo tavolo) {
		// TOOD: aggiorna i dati del tavolo (numero clienti, piatti ordinati, pazienza) in base allo stato del tavolo
		System.out.println("Unimplemented method 'view.PannelloTavolo.aggiornaTavolo'");

		
	}
	public void aggiornaProgresso(int perc) {
	    progressBar.setValue(perc);
	}

	public void aggiungiAscoltatori(ControllerTavolo al) {
	    mntmServi.addActionListener(al);
	}
}
