package restaurantsim.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;

import restaurantsim.controller.ControllerCuoco;
import restaurantsim.controller.ControllerNavigazione;
import restaurantsim.controller.ControllerNotifiche;

import java.awt.GridLayout;

import java.util.List;
import java.util.function.Function;

@SuppressWarnings("serial")
public class CucinaPanel extends JPanel {
	private BarraSuperiore barraSuperiore;
	private JPanel panelCuochi;
	private PannelloCuoco pannelloCuoco1;
	private PannelloCuoco pannelloCuoco2;
	private PannelloCuoco pannelloCuoco3;

	public CucinaPanel() {
		setLayout(new BorderLayout(0, 0));
		
		barraSuperiore = new BarraSuperiore("Vai a sala", "vai_sala_da_cucina");
		add(barraSuperiore, BorderLayout.NORTH);

		panelCuochi = new JPanel();
		panelCuochi.setBorder(new EmptyBorder(30, 30, 30, 30));
		panelCuochi.setLayout(new GridLayout(1, 3, 5, 5));
		add(panelCuochi, BorderLayout.CENTER);
		
		pannelloCuoco1 = new PannelloCuoco(1);
		panelCuochi.add(pannelloCuoco1);
		
		pannelloCuoco2 = new PannelloCuoco(2);
		panelCuochi.add(pannelloCuoco2);
		
		pannelloCuoco3 = new PannelloCuoco(3);
		panelCuochi.add(pannelloCuoco3);
	}
	
	public void aggiungiAscoltatoriNavigazione(ControllerNavigazione c) {
		barraSuperiore.getBtnCentrale().addActionListener(c);
	}
	
	public void aggiungiAscoltatoriCuochi(Function<PannelloCuoco, ControllerCuoco> creaControllerCuoco) {
		pannelloCuoco1.aggiungiAscoltatori(creaControllerCuoco.apply(pannelloCuoco1));
		pannelloCuoco2.aggiungiAscoltatori(creaControllerCuoco.apply(pannelloCuoco2));
		pannelloCuoco3.aggiungiAscoltatori(creaControllerCuoco.apply(pannelloCuoco3));
	}
	
	public void aggiornaNotifiche(List<String> list, ControllerNotifiche cn) {
		barraSuperiore.aggiornaMenuNotifiche(list, cn);
	}
}
