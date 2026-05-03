package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import restaurantsim.model.Cuoco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Piatto;
import restaurantsim.model.Sala;
import restaurantsim.model.Gioco;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.PannelloSala;

/**
 * Timer usato per la preparazione di un piatto da parte di un cuoco.
 */
public class TimerCuoco implements ActionListener {
	/**
	 * Intervallo di tempo (in ms) tra un aggiornamento e l'altro del timer.
	 */
	public static final int INTERVALLO = 1000;

	/**
	 * Gioco a cui questo timer è associato.
	 */
	private Gioco gioco;
	/**
	 * Cuoco a cui questo timer è associato.
	 */
	private Cuoco cuoco;
	/**
	 * Pannello per aggiornare la UI della sala quando il piatto è pronto.
	 */
	private PannelloSala pannelloSala;
	/**
	 * Pannello per aggiornare la UI del cuoco durante la preparazione del piatto.
	 */
	private PannelloCuoco pannelloCuoco;
	/**
	 * Controller per mostrare le notifiche quando il piatto è pronto.
	 */
	private ControllerNotifiche controllerNotifiche;
	/**
	 * Controller per gestire i suoni.
	 */
	private ControllerSuoni controllerSuoni;
	/**
	 * Timer che esegue questo ActionListener ogni {@link #INTERVALLO} ms.
	 *
	 * Usato per fermare il timer quando il piatto è pronto.
	 */
	private Timer timer;

	/**
	 * Crea un TimerCuoco associato al cuoco e ai pannelli specificati.
	 * 
	 * @param gioco               Il gioco a cui questo timer è associato
	 * @param cuoco               Il cuoco a cui questo timer è associato
	 * @param pannelloCuoco       Il pannello del cuoco per aggiornare la UI durante la preparazione del piatto
	 * @param pannelloSala        Il pannello della sala per aggiornare la UI quando il piatto è pronto
	 * @param controllerNotifiche Il controller delle notifiche per mostrare una notifica quando il piatto è pronto
	 * @param controllerSuoni     Il controller dei suoni da usare quando il piatto è pronto
	 * @param timer               Il timer che esegue questo TimerCuoco
	 */
	public TimerCuoco(Gioco gioco, Cuoco cuoco, PannelloCuoco pannelloCuoco, PannelloSala pannelloSala, ControllerNotifiche controllerNotifiche, ControllerSuoni controllerSuoni, Timer timer) {
		this.gioco = gioco;
		this.cuoco = cuoco;
		this.pannelloCuoco = pannelloCuoco;
		this.pannelloSala = pannelloSala;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerSuoni = controllerSuoni;
		this.timer = timer;
	}

	/**
	 * Eseguito ogni 1s per decrementare il tempo se ce un piatto in prepazione
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Piatto stavaPreparando = cuoco.getPiattoInPreparazione();

		cuoco.preparaPiatto();

		if (stavaPreparando.equals(Piatto.NESSUNO) && cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO))
			return;

		if (cuoco.getTempoRimanente() == 0) {
			pannelloCuoco.rimuoviImmagine();

			Sala sala = gioco.getSala();

			sala.aggiungiPiatto(stavaPreparando);

			Notifica notifica = new Notifica("Cuoco " + pannelloCuoco.getNumeroCuoco() + " ha finito di preparare " + stavaPreparando.toString() + "!", ControllerNotifiche.ORIGINE_CUCINA);
			gioco.registraNotifica(notifica);
			controllerNotifiche.mostraNotifica(notifica);

			pannelloSala.aggiornaBancone(sala.getPiattiPronti());
			timer.stop();

			controllerSuoni.playCuocoPronto();

			return;
		}

		double progresso = ((double) cuoco.getTempoRimanente()) / cuoco.getPiattoInPreparazione().getTempoDiPreparazione();
		pannelloCuoco.aggiornaProgresso(100 - (int) (progresso * 100));
	}
}
