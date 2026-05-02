package restaurantsim.view;

import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.model.Notifica;

/**
 * Barra superiore nella sala e nella cucina
 */
@SuppressWarnings("serial")
public class BarraSuperiore extends JMenuBar {
	/** Menu per "File" */
	private JMenu menuFile;
	/** Menu per "Notifiche" */
	private JMenu menuNotifiche;
	/** Bottone centrale, usato per cambiare pannello */
	private JButton btnCentrale;
	/** MenuItem "Salva partita" */
	private JMenuItem mntmSalvaPartita;
	/** MenuItem "Torna al menu" */
	private JMenuItem mntmTornaMenu;
	/** MenuItem "Esci" */
	private JMenuItem mntmEsci;

	/**
	 * Inizializza i componenti
	 * 
	 * @param testoBottone  il testo da mostrare sul pulsante centrale
	 * @param actionCommand l'action command da associare al pulsante centrale
	 */
	public BarraSuperiore(String testoBottone, String actionCommand) {
		setLayout(new GridBagLayout());

		menuFile = new JMenu("File");
		menuFile.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		// Cambia la dimensione a 0 0, se no il bottone non viene in centro
		menuFile.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints fileConstrain = new GridBagConstraints();
		// Dimensiona in X e Y (perche abbiamo anche height a 0)
		fileConstrain.fill = GridBagConstraints.BOTH;
		fileConstrain.weightx = 1;
		fileConstrain.weighty = 1;
		add(menuFile, fileConstrain);

		btnCentrale = new JButton(testoBottone);
		btnCentrale.setActionCommand(actionCommand);

		GridBagConstraints cucinaConstrain = new GridBagConstraints();
		// Dimensiona in Y, in X lascia il PreferredSize di JButton
		//  ridimensioniamo anche se non servirebbe cosi se per caso la JMenuBar diventa più alta anche il pulsante si ingrandisce
		cucinaConstrain.fill = GridBagConstraints.VERTICAL;
		cucinaConstrain.weighty = 1;
		add(btnCentrale, cucinaConstrain);

		menuNotifiche = new JMenu("Notifiche");
		menuNotifiche.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// Cambia la dimensione a 0 0, se no il bottone non viene in centro
		menuNotifiche.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints notificheConstrain = new GridBagConstraints();
		// Dimensiona in X e Y (perche abbiamo anche height a 0)
		notificheConstrain.fill = GridBagConstraints.BOTH;
		notificheConstrain.weightx = 1;
		notificheConstrain.weighty = 1;
		add(menuNotifiche, notificheConstrain);

		mntmSalvaPartita = new JMenuItem("Salva partita");
		mntmSalvaPartita.setHorizontalAlignment(SwingConstants.LEFT);
		menuFile.add(mntmSalvaPartita);

		mntmTornaMenu = new JMenuItem("Torna al menu");
		mntmTornaMenu.setHorizontalAlignment(SwingConstants.LEFT);
		mntmTornaMenu.setActionCommand(ControllerNavigazione.NAGIVA_MENU_TORNA_A_MENU);
		menuFile.add(mntmTornaMenu);

		mntmEsci = new JMenuItem("Esci");
		mntmEsci.setHorizontalAlignment(SwingConstants.LEFT);
		mntmEsci.setActionCommand(ControllerNavigazione.NAGIVA_MENU_ESCI);
		menuFile.add(mntmEsci);
	}

	/**
	 * Registra gli ascoltatori per i componenti della barra superiore
	 * 
	 * @param controllerNavigazione il controller navigazione da registrare come ascoltatore
	 */
	public void registraAscoltatoriBarraSuperiore(ControllerNavigazione controllerNavigazione) {
		mntmTornaMenu.addActionListener(controllerNavigazione);
		mntmEsci.addActionListener(controllerNavigazione);
		btnCentrale.addActionListener(controllerNavigazione);
	}

	/**
	 * Aggiorna il menu delle notifiche con le notifiche presenti nella lista
	 * 
	 * @param lista               la lista di notifiche da mostrare nel menu
	 * @param controllerNotifiche il controller notifiche da registrare come ascoltatore alle voci del menu
	 */
	public void aggiornaMenuNotifiche(List<Notifica> lista, ControllerNotifiche controllerNotifiche) {
		menuNotifiche.removeAll();

		if (!lista.isEmpty()) {
			JMenuItem deleteAll = new JMenuItem("-- CANCELLA TUTTO --");
			deleteAll.setActionCommand(ControllerNotifiche.CANCELLA_TUTTE_NOTIFICHE);
			deleteAll.addActionListener(controllerNotifiche);
			menuNotifiche.add(deleteAll);
		}

		int i = 0;
		for (Notifica notif : lista) {
			JMenuItem itemNotif = new JMenuItem(notif.getTesto());
			itemNotif.setActionCommand(String.valueOf(i));
			itemNotif.addActionListener(controllerNotifiche);
			menuNotifiche.add(itemNotif);
			i++;
		}
	}
}
