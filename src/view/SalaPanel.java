package view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SalaPanel extends JPanel {
	private PannelloTavolo panel1;
	private PannelloTavolo panel2;
	private PannelloTavolo panel3;
	private PannelloTavolo panel4;
	
	public SalaPanel() {
		setBorder(new EmptyBorder(30, 50, 30, 50));
		setLayout(new GridLayout(2, 2, 50, 50));
		
		add(panel1 = new PannelloTavolo(1));
		add(panel2 = new PannelloTavolo(2));
		add(panel3 = new PannelloTavolo(3));
		add(panel4 = new PannelloTavolo(4));
	}

	public JPanel getTavolo(int tavolo) {
		switch(tavolo) {
			case 1: return panel1;
			case 2: return panel2;
			case 3: return panel3;
			case 4: return panel4;
		}
		return null;
	}
}
