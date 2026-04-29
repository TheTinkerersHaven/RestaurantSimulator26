package restaurantsim.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.SalaPanel;

public class ControllerTavolo implements ActionListener {
    private Tavolo tavolo;
    private Timer timer;
    private PannelloTavolo pannelloTavolo;
    private SalaPanel salaPanel;
    private MainPanel mainPanel;
    private Sala sala;
    private ControllerNotifiche cn;
    
    public ControllerTavolo(Tavolo tavolo,PannelloTavolo pannelloTavolo,SalaPanel salaPanel,MainPanel mainPanel,Sala sala, ControllerNotifiche cn) {
        this.tavolo=tavolo;
        this.pannelloTavolo=pannelloTavolo;
        this.salaPanel=salaPanel;
        this.mainPanel=mainPanel;
        this.sala=sala;
        this.cn=cn;
        this.timer=new Timer(1000, new TimerTavolo(tavolo, pannelloTavolo, salaPanel, mainPanel, sala, cn));
        this.timer.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
//        if(!tavolo.isOccupato()){
//            JOptionPane.showMessageDialog(pannelloTavolo,"Nessun cliente al tavolo","Avviso",JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        Piatto piattoRichiesto = tavolo.getPiattoOrdinato();
//        if(sala.getPiattiPronti().contains(piattoRichiesto)){
//            try {
//                sala.rimuoviPiatto(piattoRichiesto);
//                tavolo.setOccupato(false);
//                tavolo.setNumeroClienti(0);
//                tavolo.setPiattoOrdinato(Piatto.NESSUNO);
//                tavolo.setPazienza(100);      
//                String notifText="Tavolo "+tavolo.getNumeroTavolo()+" ha ricevuto "+piattoRichiesto.toString(); 
//                mainPanel.mostraNotifica(notifText, cn);
//                sala.registraNotifica(notifText);
//                mainPanel.getSalaPanel().aggiornaNotifiche(sala.getNotifiche(),cn);
//                mainPanel.getCucinaPanel().aggiornaNotifiche(sala.getNotifiche(),cn);
//                pannelloTavolo.aggiornaProgresso(0);
//                salaPanel.aggiornaBancone(sala.getPiattiPronti());
//                
//            } catch (InterruptedException ie) {
//                ie.printStackTrace();
//            }
//        } 
//        else {
//            JOptionPane.showMessageDialog(pannelloTavolo,"Il piatto non e ancora pronto!","Avviso",JOptionPane.WARNING_MESSAGE);
//        }
    }
}