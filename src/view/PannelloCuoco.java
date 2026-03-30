package view;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;

import controller.PopupController;

import javax.swing.border.LineBorder;
import java.awt.Color;

@SuppressWarnings("serial")
public class PannelloCuoco extends JPanel {
	private JLabel lblCuoco;
	private JPopupMenu popupMenu;
	private JMenuItem mntmPiatto1;
	private JMenuItem mntmPiatto2;
	private JMenuItem mntmPiatto3;

	private int numeroCuoco;
	
	public PannelloCuoco(int numeroCuoco) {
		this.numeroCuoco = numeroCuoco;
		
		setLayout(new BorderLayout(0, 0));
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229), 2, false), "Cuoco " + numeroCuoco, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		lblCuoco = new JLabel();
		lblCuoco.setPreferredSize(new Dimension(100, 100));
		lblCuoco.setOpaque(true);
		add(lblCuoco);
		
		popupMenu = new JPopupMenu();
		
		mntmPiatto1 = new JMenuItem("Sashimi");
		mntmPiatto1.setActionCommand("sashimi");
		popupMenu.add(mntmPiatto1);
		
		mntmPiatto2 = new JMenuItem("Futomaki Miura");
		popupMenu.add(mntmPiatto2);
		
		mntmPiatto3 = new JMenuItem("Hosomaki maguro");
		popupMenu.add(mntmPiatto3);
	}
	
	public int getNumeroCuoco() {
		return numeroCuoco;
	}

	public void aggiungiAscoltatori(ActionListener al) {
		lblCuoco.addMouseListener(new PopupController(popupMenu));
		
		mntmPiatto1.addActionListener(al);
		mntmPiatto2.addActionListener(al);
		mntmPiatto3.addActionListener(al);
	}
}