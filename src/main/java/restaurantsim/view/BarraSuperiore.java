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

import restaurantsim.controller.ControllerNotifiche;
import restaurantsim.model.Notifica;

@SuppressWarnings("serial")
public class BarraSuperiore extends JMenuBar {
	private JMenu menuFile;
	private JMenu menuNotifiche;
	private JButton btnCentrale;
	private JMenuItem mntmSalvaPartita;
	private JMenuItem mntmTornaMenu;
	private JMenuItem mntmEsci;

	public BarraSuperiore(String buttonText, String actionCommand) {
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

		btnCentrale = new JButton(buttonText);
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
		mntmSalvaPartita.setHorizontalAlignment(SwingConstants.RIGHT);
		menuFile.add(mntmSalvaPartita);

		mntmTornaMenu = new JMenuItem("Torna al menu");
		mntmTornaMenu.setHorizontalAlignment(SwingConstants.RIGHT);
		menuFile.add(mntmTornaMenu);

		mntmEsci = new JMenuItem("Esci");
		mntmEsci.setHorizontalAlignment(SwingConstants.RIGHT);
		menuFile.add(mntmEsci);
	}

	public JButton getBtnCentrale() {
		return btnCentrale;
	}

	public void aggiornaMenuNotifiche(List<Notifica> list, ControllerNotifiche cn) {
		menuNotifiche.removeAll();

		if (!list.isEmpty()) {
			JMenuItem deleteAll = new JMenuItem("-- CANCELLA TUTTO --");
			deleteAll.setActionCommand("del_all");
			deleteAll.addActionListener(cn);
			menuNotifiche.add(deleteAll);
		}

		int i = 0;
		for (Notifica notif : list) {
			JMenuItem itemNotif = new JMenuItem(notif.getTesto());
			itemNotif.setActionCommand(String.valueOf(i));
			itemNotif.addActionListener(cn);
			menuNotifiche.add(itemNotif);
			i++;
		}
	}
}
