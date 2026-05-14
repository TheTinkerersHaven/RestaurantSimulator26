package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import restaurantsim.model.Cuoco;
import restaurantsim.model.Gioco;
import restaurantsim.model.Piatto;
import restaurantsim.view.PannelloCucina;
import restaurantsim.view.PannelloCuoco;

/**
 * ActionListener che gestisce le azioni dei cuochi.
 * 
 * Questo ascoltatore gestisce l'inizio della preparazione di un piatto da parte di un cuoco.
 */
public class ControllerCuoco implements ActionListener {
	/** Action command per preparare il Sashimi */
	public static final String SASHIMI_ACTION_COMMAND = "sashimi";
	/** Action command per preparare il Uramaki Rainbow */
	public static final String URAMAKI_RAINBOW_ACTION_COMMAND = "uramakiRainbow";
	/** Action command per preparare il Hosomaki Maguro */
	public static final String HOSOMAKI_MAGURO_ACTION_COMMAND = "hosomakiMaguro";

	/**
	 * Il cuoco a cui è associato questo controller.
	 */
	private Cuoco cuoco;
	/**
	 * L'interfaccia grafica del cuoco per mostrare i progressi in preparazione del piatto.
	 */
	private PannelloCuoco pannelloCuoco;
	/**
	 * Timer che fa progredire il processo di preparazione del piatto e aggiorna l'interfaccia grafica di conseguenza.
	 */
	private Timer timerPreparazione;

	/**
	 * Costruttore del controller del cuoco.
	 * 
	 * @param cuoco             Il cuoco a cui è associato questo controller.
	 * @param pannelloCuoco     L'interfaccia grafica del cuoco per mostrare i progressi in preparazione del piatto.
	 * @param timerPreparazione Timer che fa progredire il processo di preparazione del piatto.
	 */
	public ControllerCuoco(Cuoco cuoco, PannelloCuoco pannelloCuoco, Timer timerPreparazione) {
		this.cuoco = cuoco;
		this.pannelloCuoco = pannelloCuoco;
		this.timerPreparazione = timerPreparazione;
	}

	/**
	 * Viene cliccato un menuItem per prepare un piatto
	 *
	 * @param e L'evento di azione generato dal click sul piatto da preparare.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO)) {
			JOptionPane.showMessageDialog(pannelloCuoco, "C'è un piatto già in preparazione", "Avviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String actionCommand = e.getActionCommand();
		if (SASHIMI_ACTION_COMMAND.equals(actionCommand)) {
			cuoco.iniziaPreparazione(Piatto.SASHIMI);
		} else if (URAMAKI_RAINBOW_ACTION_COMMAND.equals(actionCommand)) {
			cuoco.iniziaPreparazione(Piatto.URAMAKI_RAINBOW);
		} else if (HOSOMAKI_MAGURO_ACTION_COMMAND.equals(actionCommand)) {
			cuoco.iniziaPreparazione(Piatto.HOSOMAKI_MAGURO);
		}

		timerPreparazione.start();
		pannelloCuoco.mostraPiatto(cuoco.getPiattoInPreparazione().getImmaginePiatto());
	}

	/**
	 * Registra i controller dei cuochi al mainPanel.
	 *
	 * @param gioco             Il gioco da cui prendere i cuochi.
	 * @param cucinaPanel       Il pannello della cucina che contiene i pannelli dei cuochi.
	 * @param controllerPartita Il controller della partita da cui prendere i timer dei cuochi.
	 */
	public static void registraAscoltatori(final Gioco gioco, PannelloCucina cucinaPanel, final ControllerPartita controllerPartita) {
		cucinaPanel.aggiungiAscoltatoriCuochi(new restaurantsim.view.Function<PannelloCuoco, ControllerCuoco>() {
			@Override
			public ControllerCuoco apply(PannelloCuoco pannelloCuoco) {
				int numeroCuoco = pannelloCuoco.getNumeroCuoco();
				Cuoco cuoco = gioco.getCuoco(numeroCuoco);
				return new ControllerCuoco(cuoco, pannelloCuoco, controllerPartita.getTimerCuoco(numeroCuoco));
			}
		});
	}
}
