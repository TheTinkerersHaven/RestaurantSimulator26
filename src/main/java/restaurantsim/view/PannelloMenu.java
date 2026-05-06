package restaurantsim.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import restaurantsim.controller.ControllerNavigazione;

/**
 * Pannello che mostra il menu principale del gioco, con un'immagine e i pulsanti per accedere alla classifica o iniziare una nuova partita
 */
public class PannelloMenu extends JPanel {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	/** Pannello che contiene l'immagine del menu */
	private JPanel panelMenu;
	/** Pannello che contiene i pulsanti del menu */
	private JPanel panelPulsantiMenu;
	/** Label per mostrare l'immagine del menu */
	private JLabel lblMenuImage;
	/** Bottone per accedere alla classifica */
	private JButton btnClassifica;
	/** Bottone per iniziare una nuova partita */
	private JButton btnNuovaPartita;
	/** Bottone per caricare una partita salvata */
	private JButton btnCaricaPartita;
	/** Bottone per andare alle impostazioni */
	private JButton btnImpostazioni;

	/** Inizializza i componenti */
	public PannelloMenu() {
		setLayout(new BorderLayout(0, 0));
		panelMenu = new JPanel();
		panelMenu.setLayout(new BorderLayout(0, 0));
		add(panelMenu);

		lblMenuImage = new JLabel(new ScaledImageIcon(PannelloPrincipale.class.getResource("/images/Restaurant Simulator V2.png")));
		lblMenuImage.setOpaque(true);
		panelMenu.add(lblMenuImage, BorderLayout.CENTER);

		panelPulsantiMenu = new JPanel();
		add(panelPulsantiMenu, BorderLayout.SOUTH);
		
		btnImpostazioni = new JButton("Impostazioni");
		btnImpostazioni.setActionCommand(ControllerNavigazione.NAVIGA_A_IMPOSTAZIONI);
		panelPulsantiMenu.add(btnImpostazioni);

		btnClassifica = new JButton("Classifica");
		btnClassifica.setActionCommand(ControllerNavigazione.NAVIGA_CLASSIFICA);
		panelPulsantiMenu.add(btnClassifica);

		btnNuovaPartita = new JButton("Nuova partita");
		btnNuovaPartita.setActionCommand(ControllerNavigazione.NAVIGA_NUOVA_PARTITA);
		panelPulsantiMenu.add(btnNuovaPartita);
		
		btnCaricaPartita = new JButton("Carica partita");
		btnCaricaPartita.setActionCommand(ControllerNavigazione.NAVIGA_CARICA_PARTITA);
		panelPulsantiMenu.add(btnCaricaPartita);
	}

	/**
	 * Abilita o disabilita il pulsante per caricare la partita.
	 * 
	 * @param active true per abilitare il pulsante, false altrimenti
	 */
	public void statoPulsanteCaricaPartita(boolean active) {
		btnCaricaPartita.setEnabled(active);
	}

	/**
	 * Registra gli ascoltatori per la navigazione per i componenti di questo pannello
	 * 
	 * @param controllerNavigazione il controller navigazione da registrare come ascoltatore per i pulsanti del menu
	 */
	public void registraAscoltatoriNavigazione(ControllerNavigazione controllerNavigazione) {
		btnImpostazioni.addActionListener(controllerNavigazione);
		btnClassifica.addActionListener(controllerNavigazione);
		btnNuovaPartita.addActionListener(controllerNavigazione);
		btnCaricaPartita.addActionListener(controllerNavigazione);
	}
}
