package restaurantsim.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerNavigazione;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

/**
 * Pannello che mostra la classifica dei giocatori
 */
@SuppressWarnings("serial")
public class ClassificaPanel extends JPanel {
	/** Lista per mostrare la classifica */
	private JList<String> listClassifica;
	/** Modello della lista per la classifica */
	private DefaultListModel<String> dlm;
	/** Pannello per i pulsanti della classifica */
	private JPanel panelPulsantiClassifica;
	/** Bottone per tornare indietro */
	private JButton btnIndietro;
	/** Etichetta del titolo della classifica */
	private JLabel lblClassifica;

	/** Inizializza i componenti */
	public ClassificaPanel() {
		setBorder(new EmptyBorder(10, 10, 0, 10));
		setLayout(new BorderLayout(0, 0));
		
		lblClassifica = new JLabel("Classifica");
		lblClassifica.setBorder(new EmptyBorder(0, 0, 5, 0));
		lblClassifica.setFont(new Font("Dialog", Font.BOLD, 20));
		lblClassifica.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblClassifica, BorderLayout.NORTH);

		listClassifica = new JList<String>();
		add(listClassifica, BorderLayout.CENTER);

		dlm = new DefaultListModel<String>();
		listClassifica.setModel(dlm);

		panelPulsantiClassifica = new JPanel();
		add(panelPulsantiClassifica, BorderLayout.SOUTH);

		btnIndietro = new JButton("Indietro");
		btnIndietro.setActionCommand(ControllerNavigazione.NAVIGA_INDIETRO_CLASSIFICA);
		panelPulsantiClassifica.add(btnIndietro);
	}

	/**
	 * Aggiorna la classifica con i dati presenti nella lista
	 *
	 * @param lista la lista di stringhe da mostrare nella classifica
	 */
	public void aggiornaClassifica(List<String> lista) {
		dlm.clear();
		dlm.addAll(lista);
	}

	/**
	 * Registra gli ascoltatori per la navigazione per i componenti del pannello classifica
	 * 
	 * @param controllerNavigazione il controller navigazione da registrare come ascoltatore per il pulsante indietro
	 */
	public void registraAscoltatoriNavigaione(ControllerNavigazione controllerNavigazione) {
		btnIndietro.addActionListener(controllerNavigazione);
	}
}
