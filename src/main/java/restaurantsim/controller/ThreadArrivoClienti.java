package restaurantsim.controller;

import java.util.Random;

import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.MainPanel;

public class ThreadArrivoClienti extends Thread {
    private Sala sala;
    private MainPanel mainPanel;
    private Tavolo[] tavoli;//4 tavoli quindi uso un array
    private ControllerNotifiche cn;
    private boolean running;
    private Random random;

    public ThreadArrivoClienti(Sala sala,MainPanel mainPanel,Tavolo[] tavoli,ControllerNotifiche cn) {
        this.sala=sala;
        this.mainPanel=mainPanel;
        this.tavoli=tavoli;
        this.cn=cn;
        this.running=true;
        this.random=new Random();
    }
    @Override
    public void run() {
        while (running) {
            try {
                int attesa=8000+random.nextInt(7000);//da 8 a 15 secondi
                Thread.sleep(attesa);
                for(Tavolo t : tavoli){
                    if(!t.isOccupato()){
                        try {
                            t.faiArrivareClienti();
                            String notifText="Clienti arrivati al Tavolo"+t.getNumeroTavolo()+"("+t.getNumeroClienti()+" persone) ordinano:"+t.getPiattoOrdinato();
                            mainPanel.mostraNotifica(sala.getNotifiche(), notifText ,cn);
                            sala.registraNotifica(notifText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                mainPanel.getSalaPanel().aggiornaNotifiche(sala.getNotifiche(),cn);
                mainPanel.getCucinaPanel().aggiornaNotifiche(sala.getNotifiche(),cn);
            } catch (InterruptedException e) {
            	e.printStackTrace();
            }
            }
        }
    
    public void ferma() {
        running = false;
        this.interrupt();
    }
}