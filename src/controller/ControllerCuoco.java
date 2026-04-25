package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.Cuoco;
import model.Piatto;
import view.MainPanel;
import view.PannelloCuoco;

public class ControllerCuoco implements ActionListener {
	private Cuoco cuoco;
	private Timer timer;
	private PannelloCuoco pc;
	
	public ControllerCuoco(Cuoco cuoco, PannelloCuoco pc) {
		this.cuoco = cuoco;
		this.pc = pc;
		this.timer = new Timer(1000, new TimerCuoco(cuoco, pc));
		this.timer.start();
	}

	/**
	 * Viene cliccato un menuItem per prepare un piatto
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO)) {
			JOptionPane.showMessageDialog(pc, "C'è un piatto già in preparazione", "Avviso", JOptionPane.WARNING_MESSAGE);
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
		
		pc.mostraPiatto(cuoco.getPiattoInPreparazione().getImmaginePiatto());
	}

	/**
	 * Registra i controller dei cuochi al mainPanel
	 * Crea un nuovo cuoco per ogni pannelloCuoco che verrà creato
	 */
	public static void registraAscoltatori(MainPanel mp) {
		mp.registraAscolatoriCuochiMain((pannelloCuoco) -> {
			Cuoco c = new Cuoco();
			return new ControllerCuoco(c, pannelloCuoco);
		});
	}
}

	