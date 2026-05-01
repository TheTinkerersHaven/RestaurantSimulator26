package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import restaurantsim.model.Cuoco;
import restaurantsim.model.Gioco;
import restaurantsim.model.Piatto;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.SalaPanel;

public class ControllerCuoco implements ActionListener {
	private Cuoco cuoco;
	private Timer timer;
	private PannelloCuoco pannelloCuoco;

	public ControllerCuoco(Cuoco cuoco, PannelloCuoco pannelloCuoco, SalaPanel ps, Gioco gioco, ControllerNotifiche controllerNotifiche) {
		this.cuoco = cuoco;
		this.pannelloCuoco = pannelloCuoco;
		this.timer = new Timer(1000, new TimerCuoco(cuoco, pannelloCuoco, ps, gioco, controllerNotifiche));
		this.timer.start();
	}

	/**
	 * Viene cliccato un menuItem per prepare un piatto
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO)) {
			JOptionPane.showMessageDialog(pannelloCuoco, "C'è un piatto già in preparazione", "Avviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		switch (e.getActionCommand()) {
			case "sashimi":
				cuoco.iniziaPreparazione(Piatto.SASHIMI);
				break;
			case "uramakiRainbow":
				cuoco.iniziaPreparazione(Piatto.URAMAKI_RAINBOW);
				break;
			case "hosomakiMaguro":
				cuoco.iniziaPreparazione(Piatto.HOSOMAKI_MAGURO);
				break;
		}

		pannelloCuoco.mostraPiatto(cuoco.getPiattoInPreparazione().getImmaginePiatto());
	}

	/**
	 * Registra i controller dei cuochi al mainPanel Crea un nuovo cuoco per ogni pannelloCuoco che verrà creato
	 */
	public static void registraAscoltatori(MainPanel mp, SalaPanel ps, Gioco gioco, ControllerNotifiche controllerNotifiche) {
		mp.registraAscoltatoriCuochiMain((pannelloCuoco) -> {
			Cuoco cuoco = gioco.getCuoco(pannelloCuoco.getNumeroCuoco());
			return new ControllerCuoco(cuoco, pannelloCuoco, ps, gioco, controllerNotifiche);
		});
	}
}
