package restaurantsim.view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerNavigazione;

import javax.swing.JButton;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

/**
 * Pannello che mostra le impostazioni del gioco, permettendo di gestire la classifica e i salvataggi.
 */
@SuppressWarnings("serial")
public class PannelloImpostazioni extends JPanel {
	/** Etichetta del titolo del pannello */
	private JLabel lblImpostazioniGioco;
	/** Pulsante per svuotare la classifica */
	private JButton btnSvuotaClassifica;
	/** Pulsante per eliminare il file di salvataggio */
	private JButton btnEliminaDatiSalvati;
	/** Spaziatore verticale */
	private Component verticalStrut;
	/** Colla verticale per il layout */
	private Component verticalGlue;
	/** Colla verticale per il layout */
	private Component verticalGlue_1;
	/** Pannello interno che contiene i pulsanti delle impostazioni */
	private JPanel panelInternoImpostazioni;
	/** Pannello che contiene i pulsanti di navigazione */
	private JPanel panelNavButtonsImpostazioni;
	/** Pulsante per tornare indietro */
	private JButton btnIndietro;
	private JCheckBox chckbxEffettiSonori;
	private Component verticalStrut_1;

	/**
	 * Inizializza i componenti del pannello impostazioni.
	 */
	public PannelloImpostazioni() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0, 0));
		
		lblImpostazioniGioco = new JLabel("Impostazioni gioco");
		lblImpostazioniGioco.setHorizontalAlignment(SwingConstants.CENTER);
		lblImpostazioniGioco.setBorder(new EmptyBorder(0, 0, 0, 0));
		lblImpostazioniGioco.setFont(new Font("Dialog", Font.BOLD, 20));
		lblImpostazioniGioco.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lblImpostazioniGioco, BorderLayout.NORTH);
		
		panelInternoImpostazioni = new JPanel();
		add(panelInternoImpostazioni);
		panelInternoImpostazioni.setLayout(new BoxLayout(panelInternoImpostazioni, BoxLayout.Y_AXIS));
		
		verticalGlue = Box.createVerticalGlue();
		panelInternoImpostazioni.add(verticalGlue);
		
		chckbxEffettiSonori = new JCheckBox("Effetti sonori");
		chckbxEffettiSonori.setSelected(true);
		chckbxEffettiSonori.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelInternoImpostazioni.add(chckbxEffettiSonori);
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		panelInternoImpostazioni.add(verticalStrut_1);
		
		btnSvuotaClassifica = new JButton("Elimina i dati salvati in classifica");
		panelInternoImpostazioni.add(btnSvuotaClassifica);
		btnSvuotaClassifica.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSvuotaClassifica.setActionCommand(ControllerNavigazione.PULISCI_CLASSIFICA);
		
		verticalStrut = Box.createVerticalStrut(20);
		panelInternoImpostazioni.add(verticalStrut);
		
		btnEliminaDatiSalvati = new JButton("Elimina i dati di salvataggio della partita");
		panelInternoImpostazioni.add(btnEliminaDatiSalvati);
		btnEliminaDatiSalvati.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnEliminaDatiSalvati.setActionCommand(ControllerNavigazione.ELIMINA_SALVATAGGIO);
		
		verticalGlue_1 = Box.createVerticalGlue();
		panelInternoImpostazioni.add(verticalGlue_1);
		
		panelNavButtonsImpostazioni = new JPanel();
		add(panelNavButtonsImpostazioni, BorderLayout.SOUTH);
		
		btnIndietro = new JButton("Indietro");
		btnIndietro.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnIndietro.setActionCommand(ControllerNavigazione.NAVIGA_DA_IMPOSTAZIONI);
		panelNavButtonsImpostazioni.add(btnIndietro);
	}

	/**
	 * Registra i listener per i componenti del pannello.
	 * 
	 * @param controllerNavigazione il controller che gestisce la navigazione
	 */
	public void registraAscoltatori(ControllerNavigazione controllerNavigazione) {
		chckbxEffettiSonori.addItemListener(controllerNavigazione);
		btnSvuotaClassifica.addActionListener(controllerNavigazione);
		btnEliminaDatiSalvati.addActionListener(controllerNavigazione);
		btnIndietro.addActionListener(controllerNavigazione);
	}
}
