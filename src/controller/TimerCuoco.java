package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Cuoco;
import model.Piatto;
import model.Sala;
import view.MainPanel;
import view.PannelloCuoco;
import view.SalaPanel;

public class TimerCuoco implements ActionListener {
	private Cuoco cuoco;
	private PannelloCuoco pc;
	private SalaPanel ps;
	private MainPanel mp;
	private Sala sala;
	private ControllerNotifiche cn;

	public TimerCuoco(Cuoco cuoco, PannelloCuoco pc, SalaPanel ps, MainPanel mp, Sala sala, ControllerNotifiche cn) {
		this.cuoco = cuoco;
		this.pc = pc;
		this.ps = ps;
		this.mp = mp;
		this.sala = sala;
		this.cn = cn;
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
				String notifText = "Cuoco " + pc.getNumeroCuoco() + " ha finito di preparare " + stavaPreparando.toString() + "!"; 
				mp.mostraNotifica(notifText, cn);
				sala.registraNotifica(notifText);
				mp.getSalaPanel().aggiornaNotifiche(sala.getNotifiche(), cn);
				mp.getCucinaPanel().aggiornaNotifiche(sala.getNotifiche(), cn);
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
