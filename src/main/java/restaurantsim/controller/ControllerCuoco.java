package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import restaurantsim.model.Cuoco;
import restaurantsim.model.Gioco;
import restaurantsim.model.Piatto;
import restaurantsim.view.CucinaPanel;
import restaurantsim.view.PannelloCuoco;

public class ControllerCuoco implements ActionListener {
	private Cuoco cuoco;
	private PannelloCuoco pannelloCuoco;
	private Timer timerPreparazione;

	public ControllerCuoco(Cuoco cuoco, PannelloCuoco pannelloCuoco, Timer timerPreparazione) {
		this.cuoco = cuoco;
		this.pannelloCuoco = pannelloCuoco;
		this.timerPreparazione = timerPreparazione;
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

		timerPreparazione.start();
		pannelloCuoco.mostraPiatto(cuoco.getPiattoInPreparazione().getImmaginePiatto());
	}

	/**
	 * Registra i controller dei cuochi al mainPanel Crea un nuovo cuoco per ogni pannelloCuoco che verrà creato
	 */
	public static void registraAscoltatori(CucinaPanel cucinaPanel, Gioco gioco, ControllerPartita controllerPartita) {
		cucinaPanel.aggiungiAscoltatoriCuochi((pannelloCuoco) -> {
			int numeroCuoco = pannelloCuoco.getNumeroCuoco();
			Cuoco cuoco = gioco.getCuoco(numeroCuoco);
			return new ControllerCuoco(cuoco, pannelloCuoco, controllerPartita.getTimerCuoco(numeroCuoco));
		});
	}
}
