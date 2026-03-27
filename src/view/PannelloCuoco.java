package view;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class PannelloCuoco extends JPanel {
	private JLabel lblCuoco;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPiatto1;
	private JMenuItem mntmPiatto2;
	private JMenuItem mntmPiatto3;
	
	public PannelloCuoco(int numeroCuoco) {
		setLayout(new BorderLayout(0, 0));
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229), 2, false), "Cuoco " + numeroCuoco, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		lblCuoco = new JLabel();
		lblCuoco.setPreferredSize(new Dimension(100, 100));
		lblCuoco.setOpaque(true);
		add(lblCuoco);
		
		popupMenu = new JPopupMenu();
		addPopup(lblCuoco, popupMenu);
		
		mntmPiatto1 = new JMenuItem("Sashimi");
		popupMenu.add(mntmPiatto1);
		
		mntmPiatto2 = new JMenuItem("Futomaki Miura");
		popupMenu.add(mntmPiatto2);
		
		mntmPiatto3 = new JMenuItem("Hosomaki maguro");
		popupMenu.add(mntmPiatto3);
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}