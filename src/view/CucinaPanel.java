package view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.border.EmptyBorder;

import controller.ControllerNavigazione;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class CucinaPanel extends JPanel {
	private BarraSuperiore barraSuperiore;
	private JPanel panelCuochi;
	private PannelloCuoco pannelloCuoco1;
	private PannelloCuoco pannelloCuoco2;
	private PannelloCuoco pannelloCuoco3;
	private JPanel panel;

	public CucinaPanel() {
		setLayout(new BorderLayout(0, 0));
		
		barraSuperiore = new BarraSuperiore("Vai a sala", "vai_sala_da_cucina");
		add(barraSuperiore, BorderLayout.NORTH);
		
		panel = new JPanel();
		panel.setBorder(new EmptyBorder(30, 30, 30, 30));
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panelCuochi = new JPanel();
		panel.add(panelCuochi);
		panelCuochi.setMaximumSize(new Dimension(32767, 32767));
		panelCuochi.setLayout(new GridLayout(1, 3, 5, 5));
		
		pannelloCuoco1 = new PannelloCuoco(1);
		panelCuochi.add(pannelloCuoco1);
		
		pannelloCuoco2 = new PannelloCuoco(2);
		panelCuochi.add(pannelloCuoco2);
		
		pannelloCuoco3 = new PannelloCuoco(3);
		panelCuochi.add(pannelloCuoco3);
	}
	
	public void aggiungiAscoltatori(ControllerNavigazione c/*, Function<PannelloCuoco, ActionListener> creaAscoltatore*/) {
//		pannelloCuoco1.aggiungiAscoltatori(creaAscoltatore.apply(pannelloCuoco1));
//		pannelloCuoco2.aggiungiAscoltatori(creaAscoltatore.apply(pannelloCuoco2));
//		pannelloCuoco3.aggiungiAscoltatori(creaAscoltatore.apply(pannelloCuoco3));
		barraSuperiore.getBtnCentrale().addActionListener(c);
	}
}
