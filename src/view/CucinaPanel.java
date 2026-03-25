package view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.border.EmptyBorder;
import javax.swing.Box;

@SuppressWarnings("serial")
public class CucinaPanel extends JPanel {
	private JPanel panel;
	private JButton btnPiatto1;
	private JButton btnPiatto2;
	private JButton btnPiatto3;
	private JButton btnPiatto4;
	private JButton btnPiatto5;
	private JButton btnIndietro;
	private Component verticalGlue;

	public CucinaPanel() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		panel = new JPanel();
		add(panel);
		
		btnPiatto1 = new JButton("Piatto1");
		panel.add(btnPiatto1);
		
		btnPiatto2 = new JButton("Piatto2");
		panel.add(btnPiatto2);
		
		btnPiatto3 = new JButton("Piatto3");
		panel.add(btnPiatto3);
		
		btnPiatto4 = new JButton("Piatto4");
		panel.add(btnPiatto4);
		
		btnPiatto5 = new JButton("Piatto5");
		panel.add(btnPiatto5);
		
		btnIndietro = new JButton("Indietro");
		btnIndietro.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnIndietro);
	}
}
