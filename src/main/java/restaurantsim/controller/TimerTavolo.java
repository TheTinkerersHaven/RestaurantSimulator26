package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public TimerTavolo(Tavolo tavolo, SalaPanel salaPanel, PannelloTavolo pannelloTavolo, Gioco gioco, ControllerNotifiche controllerNotifiche, ControllerNavigazione controllerNavigazione) {
		this.tavolo = tavolo;
		this.salaPanel = salaPanel;
		this.pannelloTavolo = pannelloTavolo;
		this.gioco = gioco;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerNavigazione = controllerNavigazione;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!tavolo.isOccupato()) {
			return;
		}

		try {
			boolean arrabbiato = tavolo.decrementaPazienza();

			if (arrabbiato) {
				gioco.aggiungiPunteggio(Gioco.PUNTEGGIO_PER_CLIENTE_ARRABBIATO);
				gioco.incrementaClientiArrabbiati();

				if (gioco.getClientiArrabbiati() == Gioco.MAX_CLIENTI_ARRABBIATI) {
					controllerNavigazione.finisciPartita(ControllerNavigazione.ESCI_NOME_E_SCONFITTA);
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
