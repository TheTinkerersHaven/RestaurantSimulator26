package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Tavolo;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.SalaPanel;

public class TimerTavolo implements ActionListener {
	private Tavolo tavolo;
	private SalaPanel salaPanel;
	private PannelloTavolo pannelloTavolo;
	private Gioco gioco;
	private ControllerNotifiche controllerNotifiche;
	private ControllerNavigazione controllerNavigazione;
	private ControllerPartita controllerPartita;
	private Timer timer;

	public TimerTavolo(Tavolo tavolo, SalaPanel salaPanel, PannelloTavolo pannelloTavolo, Gioco gioco, ControllerNotifiche controllerNotifiche, ControllerNavigazione controllerNavigazione, ControllerPartita controllerPartita, Timer timer) {
		this.tavolo = tavolo;
		this.salaPanel = salaPanel;
		this.pannelloTavolo = pannelloTavolo;
		this.gioco = gioco;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerNavigazione = controllerNavigazione;
		this.controllerPartita = controllerPartita;
		this.timer = timer;
	}

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
			ie.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
