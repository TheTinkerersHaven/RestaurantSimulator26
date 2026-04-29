package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import restaurantsim.model.Piatto;
import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.SalaPanel;

public class TimerTavolo implements ActionListener {
    private Tavolo tavolo;
    private PannelloTavolo pannelloTavolo;
    private SalaPanel salaPanel;
    private MainPanel mainPanel;
    private Sala sala;
    private ControllerNotifiche cn;
    
    public TimerTavolo(Tavolo tavolo,PannelloTavolo pannelloTavolo,SalaPanel salaPanel,MainPanel mainPanel,Sala sala,ControllerNotifiche cn) {
        this.tavolo=tavolo;
        this.pannelloTavolo=pannelloTavolo;
        this.salaPanel=salaPanel;
        this.mainPanel=mainPanel;
        this.sala=sala;
        this.cn=cn;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!tavolo.isOccupato()) {
            return;
        }       
        try {
            tavolo.decrementaPazienza();
            double progresso=(double)tavolo.getPazienza()/100;
            pannelloTavolo.aggiornaProgresso(100-(int)(progresso * 100));
            if (tavolo.getPazienza()==0) {
                String notifText="Tavolo "+tavolo.getNumeroTavolo()+" il cliente si e arrabbiato"; 
                mainPanel.mostraNotifica(sala.getNotifiche(), notifText, cn);
                sala.registraNotifica(notifText);
                tavolo.setOccupato(false);
                tavolo.setNumeroClienti(0);
                tavolo.setPiattoOrdinato(Piatto.NESSUNO);
                tavolo.setPazienza(100);
                mainPanel.getSalaPanel().aggiornaNotifiche(sala.getNotifiche(),cn);
                mainPanel.getCucinaPanel().aggiornaNotifiche(sala.getNotifiche(),cn);
                pannelloTavolo.aggiornaProgresso(0);
                salaPanel.aggiornaBancone(sala.getPiattiPronti());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}