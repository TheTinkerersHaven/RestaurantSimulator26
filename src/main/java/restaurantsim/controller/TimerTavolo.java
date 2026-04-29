package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.model.Notifica;
import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.PannelloTavolo;

public class TimerTavolo implements ActionListener {
    private Tavolo tavolo;
    private PannelloTavolo pannelloTavolo;
    private Sala sala;
    private ControllerNotifiche controllerNotifiche;

    public TimerTavolo(Tavolo tavolo, PannelloTavolo pannelloTavolo, Sala sala, ControllerNotifiche controllerNotifiche) {
        this.tavolo = tavolo;
        this.pannelloTavolo = pannelloTavolo;
        this.sala = sala;
        this.controllerNotifiche = controllerNotifiche;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!tavolo.isOccupato()) {
            return;
        }

        try {
            boolean arrabbiato = tavolo.decrementaPazienza();

            if (arrabbiato) {
                Notifica notifica = new Notifica("Il cliente al tavolo " + tavolo.getNumeroTavolo() + " è arrabbiato!", ControllerNotifiche.ORIGINE_SALA);
                sala.registraNotifica(notifica);
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
