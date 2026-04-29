package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.model.Cuoco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Piatto;
import restaurantsim.model.Sala;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.SalaPanel;

public class TimerCuoco implements ActionListener {
	private Cuoco cuoco;
	private PannelloCuoco pc;
	private SalaPanel ps;
	private Sala sala;
	private ControllerNotifiche controllerNotifiche;

	public TimerCuoco(Cuoco cuoco, PannelloCuoco pc, SalaPanel ps, Sala sala, ControllerNotifiche cn) {
		this.cuoco = cuoco;
		this.pc = pc;
		this.ps = ps;
		this.sala = sala;
		this.controllerNotifiche = cn;
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

				Notifica notifica = new Notifica("Cuoco " + pc.getNumeroCuoco() + " ha finito di preparare " + stavaPreparando.toString() + "!", ControllerNotifiche.ORIGINE_CUCINA);
				sala.registraNotifica(notifica);
				controllerNotifiche.mostraNotifica(notifica);
			} catch (InterruptedException ie) {
				return;
			}

			ps.aggiornaBancone(sala.getPiattiPronti());

			return;
		}

		double progresso = ((double) cuoco.getTempoRimanente()) / cuoco.getPiattoInPreparazione().getTempoDiPreparazione();
		pc.aggiornaProgresso(100 - (int) (progresso * 100));
	}
}
