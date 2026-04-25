package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Cuoco;
import model.Piatto;
import model.Sala;
import view.PannelloCuoco;
import view.SalaPanel;

public class TimerCuoco implements ActionListener {
	private Cuoco cuoco;
	private PannelloCuoco pc;
	private SalaPanel ps;
	private Sala sala;

	public TimerCuoco(Cuoco cuoco, PannelloCuoco pc, SalaPanel ps, Sala sala) {
		this.cuoco = cuoco;
		this.pc = pc;
		this.ps = ps;
		this.sala = sala;
	}

	/**
	 * Eseguito ogni 1s per decrementare il tempo se ce un piatto in prepazione
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Piatto stavaPreparando = cuoco.getPiattoInPreparazione();

		cuoco.run();

		if (stavaPreparando.equals(Piatto.NESSUNO) && cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO))
			return;

		if (cuoco.getTempoRimanente() == 0) {
			pc.rimuoviImmagine();
			try {
				sala.aggiungiPiatto(stavaPreparando);
			} catch (InterruptedException ie) {
				// TODO: gestisci?
				ie.printStackTrace();
			}

			ps.aggiornaBancone(sala.getPiattiPronti());

			return;
		}

		double progresso = ((double) cuoco.getTempoRimanente()) / cuoco.getPiattoInPreparazione().getTempoDiPreparazione();
		pc.aggiornaProgresso(100 - (int) (progresso * 100));
	}
}
