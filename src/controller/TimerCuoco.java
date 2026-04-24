package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Cuoco;
import model.Piatto;
import view.PannelloCuoco;

public class TimerCuoco implements ActionListener {
	private Cuoco cuoco;
	private PannelloCuoco pc;

	public TimerCuoco(Cuoco cuoco, PannelloCuoco pc) {
		this.cuoco = cuoco;
		this.pc = pc;
	}
	
	/**
	 * Eseguito ogni 1s per decrementare il tempo se ce un piatto in prepazione
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean stavaPreparando = !cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO);
		
		cuoco.run();
		
		if (!stavaPreparando && cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO)) return;
				
		if (cuoco.getTempoRimanente() == 0) {
			pc.rimuoviImmagine();
			return;
		}
		
		try {
			double progresso = ((double)cuoco.getTempoRimanente()) / cuoco.getPiattoInPreparazione().getTempoDiPreparazione();
			pc.aggiornaProgresso(100 - (int)(progresso * 100));
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(pc, ex.getMessage(), "Errore!", JOptionPane.ERROR_MESSAGE);
		}
	}
}