package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.model.Cuoco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Piatto;
import restaurantsim.model.Sala;
import restaurantsim.model.Gioco;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.SalaPanel;

public class TimerCuoco implements ActionListener {
	private Cuoco cuoco;
	private PannelloCuoco pc;
	private SalaPanel ps;
	private Gioco gioco;
	private ControllerNotifiche controllerNotifiche;

	public TimerCuoco(Cuoco cuoco, PannelloCuoco pc, SalaPanel ps, Gioco gioco, ControllerNotifiche controllerNotifiche) {
		this.cuoco = cuoco;
		this.pc = pc;
		this.ps = ps;
		this.gioco = gioco;
		this.controllerNotifiche = controllerNotifiche;
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

			Sala sala = gioco.getSala();

			try {
				sala.aggiungiPiatto(stavaPreparando);

				Notifica notifica = new Notifica("Cuoco " + pc.getNumeroCuoco() + " ha finito di preparare " + stavaPreparando.toString() + "!", ControllerNotifiche.ORIGINE_CUCINA);
				gioco.registraNotifica(notifica);
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
