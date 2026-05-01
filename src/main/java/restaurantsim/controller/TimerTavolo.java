package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Tavolo;
import restaurantsim.model.TavoloNonOccupatoException;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.PannelloSala;

/**
 * Timer usato per decrementare la pazienza dei clienti al tavolo.
 */
public class TimerTavolo implements ActionListener {
	/**
	 * Intervallo di tempo (in ms) tra un aggiornamento e l'altro del timer.
	 */
	public static final int INTERVALLO = 1000;

	/**
	 * Gioco a cui questo timer è associato.
	 */
	private Gioco gioco;
	/**
	 * Tavolo a cui questo timer è associato.
	 */
	private Tavolo tavolo;
	/**
	 * Pannello per aggiornare la UI della sala quando il cliente si arrabbia.
	 */
	private PannelloSala salaPanel;
	/**
	 * Pannello per aggiornare la UI del tavolo durante il decremento della pazienza.
	 */
	private PannelloTavolo pannelloTavolo;
	/**
	 * Controller per mostrare le notifiche quando il cliente si arrabbia.
	 */
	private ControllerNotifiche controllerNotifiche;
	/**
	 * Controller per gestire la navigazione tra i menu, usato per tornare al menu principale quando si perde la partita.
	 */
	private ControllerNavigazione controllerNavigazione;
	/**
	 * Controller per gestire la partita, usato per finire la partita quando si perde.
	 */
	private ControllerPartita controllerPartita;
	/**
	 * Timer che esegue questo ActionListener ogni {@link #INTERVALLO} ms.
	 * 
	 * Usato per fermare il timer quando il cliente si arrabbia o quando il tavolo viene liberato.
	 */
	private Timer timer;

	/**
	 * Crea un TimerTavolo associato al tavolo e ai pannelli specificati.
	 * 
	 * @param gioco                 Il gioco a cui questo timer è associato
	 * @param tavolo                Il tavolo a cui questo timer è associato
	 * @param salaPanel             Il pannello della sala per aggiornare la UI quando il cliente si arrabbia
	 * @param pannelloTavolo        Il pannello del tavolo per aggiornare la UI durante il decremento della pazienza
	 * @param controllerNotifiche   Il controller delle notifiche per mostrare una notifica quando il cliente si arrabbia
	 * @param controllerNavigazione Il controller di navigazione per tornare al menu principale quando si perde la partita
	 * @param controllerPartita     Il controller di partita per finire la partita quando si perde
	 * @param timer                 Il timer che esegue questo TimerTavolo
	 */
	public TimerTavolo(Gioco gioco, Tavolo tavolo, PannelloSala salaPanel, PannelloTavolo pannelloTavolo, ControllerNotifiche controllerNotifiche, ControllerNavigazione controllerNavigazione, ControllerPartita controllerPartita, Timer timer) {
		this.gioco = gioco;
		this.tavolo = tavolo;
		this.salaPanel = salaPanel;
		this.pannelloTavolo = pannelloTavolo;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerNavigazione = controllerNavigazione;
		this.controllerPartita = controllerPartita;
		this.timer = timer;
	}

	/**
	 * Decrementa la pazienza del cliente al tavolo.
	 * 
	 * Se il cliente si arrabbia, ferma il timer, aggiorna il punteggio e mostra una notifica.
	 * 
	 * Se il numero massimo di clienti arrabbiati è stato raggiunto, finisce la partita e torna al menu principale.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!tavolo.isOccupato()) {
			return;
		}

		try {
			boolean arrabbiato = tavolo.decrementaPazienza();

			if (arrabbiato) {
				timer.stop();

				gioco.aggiungiPunteggio(Gioco.PUNTEGGIO_PER_CLIENTE_ARRABBIATO);
				gioco.incrementaClientiArrabbiati();

				if (gioco.getClientiArrabbiati() == Gioco.MAX_CLIENTI_ARRABBIATI) {
					controllerPartita.finisciPartita(ControllerPartita.ESCI_NOME_E_SCONFITTA);
					controllerNavigazione.cambiaMenu("menu");
					return;
				}

				salaPanel.aggiornaPunteggio(gioco.getPunteggio());

				Notifica notifica = new Notifica("Il cliente al tavolo " + tavolo.getNumeroTavolo() + " è arrabbiato! (" + gioco.getClientiArrabbiati() + "/" + Gioco.MAX_CLIENTI_ARRABBIATI + " a fine partita)", ControllerNotifiche.ORIGINE_SALA);
				gioco.registraNotifica(notifica);
				controllerNotifiche.mostraNotifica(notifica);
			}

			pannelloTavolo.aggiornaTavolo(tavolo);
		} catch (InterruptedException ie) {
			// Ignora
		} catch (TavoloNonOccupatoException tnoe) {
			// Ignora, il tavolo è vuoto, quindi non c'è pazienza da decrementare
			// Non dovrebbe mai accadere per via della check iniziale 
		}
	}
}
