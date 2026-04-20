package view;

import java.awt.GridLayout;
import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JMenuItem;

import java.awt.Component;
import java.awt.ComponentOrientation;

@SuppressWarnings("serial")
public class SalaPanel extends JPanel {
	private PannelloTavolo tavolo1;
	private PannelloTavolo tavolo2;
	private PannelloTavolo tavolo3;
	private PannelloTavolo tavolo4;
	private JPanel panelTavoli;
	private JScrollPane scrollPaneBancone;
	private JMenuBar menuBarLeft;
	private JMenu menuFile;
	private JButton btnNewButton;
	private JMenu menuNotifiche;
	private JMenuItem mntmNewMenuItem;
	private JPanel panel;
	private JMenuBar menuBarRight;
	
	public SalaPanel() {
		setBorder(null);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 0));
		add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		menuBarLeft = new JMenuBar();
		menuBarLeft.setLayout(new BorderLayout());
		panel.add(menuBarLeft);
		
		menuFile = new JMenu("File");
		menuFile.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		menuBarLeft.add(menuFile, BorderLayout.CENTER);
		
		btnNewButton = new JButton("Vai in cucina");
		panel.add(btnNewButton);
		btnNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		menuBarRight = new JMenuBar();
		menuBarRight.setLayout(new BorderLayout());
		panel.add(menuBarRight);
		
		menuNotifiche = new JMenu("Notifiche");
		menuBarRight.add(menuNotifiche, BorderLayout.CENTER);
		menuNotifiche.setAlignmentX(Component.RIGHT_ALIGNMENT);
		menuNotifiche.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		mntmNewMenuItem = new JMenuItem("New menu item");
		mntmNewMenuItem.setHorizontalAlignment(SwingConstants.RIGHT);
		menuNotifiche.add(mntmNewMenuItem);
		
		panelTavoli = new JPanel();
		panelTavoli.setBorder(new EmptyBorder(30, 50, 30, 50));
		panelTavoli.setLayout(new GridLayout(2, 2, 50, 50));
		add(panelTavoli);
		
		add(tavolo1 = new PannelloTavolo(1));
		panelTavoli.add(tavolo1);
		add(tavolo2 = new PannelloTavolo(2));
		panelTavoli.add(tavolo2);
		add(tavolo3 = new PannelloTavolo(3));
		panelTavoli.add(tavolo3);
		add(tavolo4 = new PannelloTavolo(4));
		panelTavoli.add(tavolo4);
		
		scrollPaneBancone = new JScrollPane();
		add(scrollPaneBancone);
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
