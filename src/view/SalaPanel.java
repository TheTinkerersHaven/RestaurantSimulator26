package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import java.awt.ComponentOrientation;

@SuppressWarnings("serial")
public class SalaPanel extends JPanel {
	private PannelloTavolo tavolo1;
	private PannelloTavolo tavolo2;
	private PannelloTavolo tavolo3;
	private PannelloTavolo tavolo4;
	private JPanel panelTavoli;
	private JScrollPane scrollPaneBancone;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JButton btnVaiCucina;
	private JMenu menuNotifiche;
	private JMenuItem mntmNewMenuItem;
	
	public SalaPanel() {
		setLayout(new BorderLayout());
		
		menuBar = new JMenuBar();
		menuBar.setLayout(new GridBagLayout());
		add(menuBar, BorderLayout.NORTH);
		
		menuFile = new JMenu("File");
		menuFile.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		// Cambia la dimensione a 0 0, se no il bottone non viene in centro
		menuFile.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints fileConstrain = new GridBagConstraints();
		// Dimensiona in X e Y (perche abbiamo anche height a 0)
		fileConstrain.fill = GridBagConstraints.BOTH;
		fileConstrain.weightx = 1;
		fileConstrain.weighty = 1;
		menuBar.add(menuFile, fileConstrain);
		
		btnVaiCucina = new JButton("Vai in cucina");

		GridBagConstraints cucinaConstrain = new GridBagConstraints();
		// Dimensiona in Y, in X lascia il PreferredSize di JButton
		//  ridimensioniamo anche se non servirebbe cosi se per caso la JMenuBar diventa più alta anche il pulsante si ingrandisce
		cucinaConstrain.fill = GridBagConstraints.VERTICAL;
		cucinaConstrain.weighty = 1;
		menuBar.add(btnVaiCucina, cucinaConstrain);
		
		menuNotifiche = new JMenu("Notifiche");
		menuNotifiche.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		// Cambia la dimensione a 0 0, se no il bottone non viene in centro
		menuNotifiche.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints notificheConstrain = new GridBagConstraints();
		// Dimensiona in X e Y (perche abbiamo anche height a 0)
		notificheConstrain.fill = GridBagConstraints.BOTH;
		notificheConstrain.weightx = 1;
		notificheConstrain.weighty = 1;
		menuBar.add(menuNotifiche, notificheConstrain);
		
		mntmNewMenuItem = new JMenuItem("New menu item");
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.RIGHT);
		menuNotifiche.add(mntmNewMenuItem);
		
		panelTavoli = new JPanel();
		panelTavoli.setBorder(new EmptyBorder(30, 50, 30, 50));
		panelTavoli.setLayout(new GridLayout(2, 2, 50, 50));
		add(panelTavoli, BorderLayout.CENTER);
		
		tavolo1 = new PannelloTavolo(1);
		panelTavoli.add(tavolo1);
		tavolo2 = new PannelloTavolo(2);
		panelTavoli.add(tavolo2);
		tavolo3 = new PannelloTavolo(3);
		panelTavoli.add(tavolo3);
		tavolo4 = new PannelloTavolo(4);
		panelTavoli.add(tavolo4);
		
		scrollPaneBancone = new JScrollPane();
		scrollPaneBancone.setPreferredSize(new Dimension(100, 100));
		add(scrollPaneBancone, BorderLayout.SOUTH);
	}

	public JPanel getTavolo(int tavolo) {
		switch(tavolo) {
			case 1: return tavolo1;
			case 2: return tavolo2;
			case 3: return tavolo3;
			case 4: return tavolo4;
		}
		return null;
	}
}
