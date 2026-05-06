package restaurantsim.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.model.Notifica;

/**
 * Pannello che mostra una singola notifica
 */
public class NotificaPanel extends JPanel {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	/** Area di testo per mostrare il testo della notifica */
	private JTextArea textAreaNotif;
	/** Etichetta per il simbolo di chiusura della notifica */
	private JLabel lblCloseNotif;
	/** Pannello che contiene la notifica, usato distanziare le notifiche tra di loro */
	private JPanel panel;

	/** Origine di questa notifica, cioè da quale pannello deriva */
	private String origine;

	/**
	 * Inizializza i componenti.
	 * 
	 * @param notifica la notifica da mostrare in questo pannello
	 */
	public NotificaPanel(Notifica notifica) {
		this.origine = notifica.getOrigine();

		setBorder(new EmptyBorder(0, 0, 10, 0));
		setOpaque(false);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setAlignmentX(RIGHT_ALIGNMENT);

		panel = new JPanel();
		panel.setBackground(Color.YELLOW);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		add(panel);

		textAreaNotif = new JTextArea(notifica.getTesto());
		// Inserisco dei border direttamente sugli elementi per rendere anche gli spazi vuoti cliccabili
		textAreaNotif.setBorder(new EmptyBorder(5, 5, 5, 2));
		textAreaNotif.setName(ControllerNotifiche.NOME_TESTO_NOTIFICA);
		textAreaNotif.setOpaque(false);
		textAreaNotif.setFont(new Font("Dialog", Font.BOLD, 12));
		textAreaNotif.setEnabled(false);
		textAreaNotif.setEditable(false);
		// Di default il testo disattivato ha un colore grigio, reimposta a nero
		textAreaNotif.setDisabledTextColor(Color.BLACK);
		textAreaNotif.setAlignmentX(Component.RIGHT_ALIGNMENT);
		textAreaNotif.setBackground(Color.YELLOW);
		// getPreferredSize è calcolato in base al testo, se impostiamo il max a preferred gli diamo lo spazio minimo
		textAreaNotif.setMaximumSize(textAreaNotif.getPreferredSize());
		panel.add(textAreaNotif);

		// Simbolo unicode per la "x" di chiusura
		lblCloseNotif = new JLabel("\u2715");
		lblCloseNotif.setBorder(new EmptyBorder(3, 5, 5, 5));
		lblCloseNotif.setName(ControllerNotifiche.NOME_LABEL_CHIUDI_NOTIFICA);
		lblCloseNotif.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblCloseNotif.setFont(new Font("Dialog", Font.BOLD, 18));
		panel.add(lblCloseNotif);
	}

	/**
	 * Registra gli ascoltatori per i componenti di questo pannello
	 * 
	 * @param controllerNotifiche il controller notifiche da registrare come ascoltatore per i componenti del pannello
	 */
	public void registraAscoltatori(ControllerNotifiche controllerNotifiche) {
		textAreaNotif.addMouseListener(controllerNotifiche);
		lblCloseNotif.addMouseListener(controllerNotifiche);
	}

	/**
	 * Restituisce il pannello che contiene la notifica
	 * 
	 * @return il pannello che contiene la notifica
	 */
	public JPanel getPanel() {
		return panel;
	}

	/**
	 * Restituisce l'origine di questa notifica, cioè da quale pannello deriva
	 * 
	 * @return l'origine di questa notifica, cioè da quale pannello deriva
	 */
	public String getOrigine() {
		return origine;
	}
}
