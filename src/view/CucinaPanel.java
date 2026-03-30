package view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.function.Function;

import javax.swing.border.EmptyBorder;
import javax.swing.Box;

@SuppressWarnings("serial")
public class CucinaPanel extends JPanel {
	private JPanel panelCuochi;
	private JButton btnIndietro;
	private PannelloCuoco pannelloCuoco1;
	private PannelloCuoco pannelloCuoco2;
	private PannelloCuoco pannelloCuoco3;
	private Component verticalGlue;
	private Component verticalGlue_1;

	public CucinaPanel() {
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		verticalGlue = Box.createVerticalGlue();
		add(verticalGlue);
		
		panelCuochi = new JPanel();
		panelCuochi.setMaximumSize(new Dimension(32767, 0));
		add(panelCuochi);
		
		pannelloCuoco1 = new PannelloCuoco(1);
		panelCuochi.add(pannelloCuoco1);
		
		pannelloCuoco2 = new PannelloCuoco(2);
		panelCuochi.add(pannelloCuoco2);
		
		pannelloCuoco3 = new PannelloCuoco(3);
		panelCuochi.add(pannelloCuoco3);
		
		verticalGlue_1 = Box.createVerticalGlue();
		add(verticalGlue_1);
		
		btnIndietro = new JButton("Indietro");
		btnIndietro.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(btnIndietro);
	}
	
	public void aggiungiAscoltatori(Function<PannelloCuoco, ActionListener> creaAscoltatore) {
		pannelloCuoco1.aggiungiAscoltatori(creaAscoltatore.apply(pannelloCuoco1));
		pannelloCuoco2.aggiungiAscoltatori(creaAscoltatore.apply(pannelloCuoco2));
		pannelloCuoco3.aggiungiAscoltatori(creaAscoltatore.apply(pannelloCuoco3));
	}
}
