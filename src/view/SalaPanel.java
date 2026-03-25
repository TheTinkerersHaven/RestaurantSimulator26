package view;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SalaPanel extends JPanel {
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JPanel panel4;
	
	public SalaPanel() {
		setBorder(new EmptyBorder(30, 50, 30, 50));
		setLayout(new GridLayout(2, 2, 50, 50));
		
		add(panel1 = new JPanel());
		add(panel2 = new JPanel());
		add(panel3 = new JPanel());
		add(panel4 = new JPanel());
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
