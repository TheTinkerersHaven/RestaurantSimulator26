package restaurantsim.controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import restaurantsim.model.Classifica;
import restaurantsim.model.Cuoco;
import restaurantsim.model.Gioco;
import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.view.ClassificaPanel;
import restaurantsim.view.CucinaPanel;
import restaurantsim.view.MainPanel;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.SalaPanel;

public class ControllerPartita {
    private MainPanel panel;

    private Gioco gioco;
    private Classifica classifica;
    private ControllerNotifiche controllerNotifiche;

    private ArrayList<Timer> timerTavoli;
    private ArrayList<Timer> timerCuochi;

    private ArrivoClientiWorker arrivoClientiWorker;

    // delle constanti per rendere più chiaro il fine partita
    public static final int ESCI_SENZA_MESSAGGIO = 1;
    public static final int ESCI_SOLO_NOME = 2;
    public static final int ESCI_NOME_E_SCONFITTA = 3;

    public ControllerPartita(MainPanel panel, Gioco gioco, Classifica classifica, ControllerNotifiche controllerNotifiche) {
        this.panel = panel;
        this.gioco = gioco;
        this.classifica = classifica;
        this.controllerNotifiche = controllerNotifiche;

        this.timerTavoli = new ArrayList<>(Gioco.NUM_TAVOLI);
        this.timerCuochi = new ArrayList<>(Gioco.NUM_CUOCHI);
        this.arrivoClientiWorker = new ArrivoClientiWorker(gioco, panel, controllerNotifiche, this);
    }

    public void nuovaParita() {
        iniziaPartita();
    }

    public void finisciPartita(int status) {
        interrompiPartita();

        if (status == ESCI_SOLO_NOME) {
            ClassificaPanel classificaPanel = panel.getClassificaPanel();
            if (status == ESCI_NOME_E_SCONFITTA)
                JOptionPane.showMessageDialog(panel, "Gioco terminato! Hai fatto arrabbiare troppi clienti!", "Partita finita", JOptionPane.INFORMATION_MESSAGE);
            String nomeGiocatore;
            do {
                nomeGiocatore = JOptionPane.showInputDialog(panel, "Inserisci il nome del giocatore.", "Inserisci nome", JOptionPane.INFORMATION_MESSAGE);
                if (nomeGiocatore == null || nomeGiocatore.isBlank())
                    JOptionPane.showMessageDialog(panel, "Non puoi non inserire un nome.", "Errore", JOptionPane.OK_OPTION);
            } while (nomeGiocatore == null || nomeGiocatore.isBlank());
            classifica.inserisciPartita(nomeGiocatore, gioco.getPunteggio());
            classificaPanel.aggiornaClassifica(classifica.getClassifica());
        }

        /// Model reset
        gioco.reset();

        /// View reset
        // Notifiche
        panel.aggiornaMenuNotifiche(gioco.getNotifiche(), controllerNotifiche);

        // Sala
        SalaPanel salaPanel = panel.getSalaPanel();
        salaPanel.aggiornaPunteggio(gioco.getPunteggio());
        salaPanel.aggiornaBancone(gioco.getSala().getPiattiPronti());
        for (int i = 1; i <= Sala.NUM_TAVOLI; i++) {
            salaPanel.getPannelloTavolo(i).aggiornaTavolo(gioco.getSala().getTavolo(i));
        }

        // Cucina
        CucinaPanel cucinaPanel = panel.getCucinaPanel();
        for (int i = 1; i <= Gioco.NUM_CUOCHI; i++) {
            PannelloCuoco pannelloCuoco = cucinaPanel.getPannelloCuoco(i);
            pannelloCuoco.aggiornaProgresso(0);
            pannelloCuoco.rimuoviImmagine();
        }
    }

    private void iniziaPartita() {
        arrivoClientiWorker.execute();
    }

    private void interrompiPartita() {
        arrivoClientiWorker.cancel(true);

        for (Timer timer : timerTavoli) {
            timer.stop();
        }

        for (Timer timer : timerCuochi) {
            timer.stop();
        }
    }

    public void initializzaTimerTavoli(SalaPanel salaPanel, ControllerNavigazione controllerNavigazione) {
        for (int i = 1; i <= Sala.NUM_TAVOLI; i++) {
            Tavolo tavolo = gioco.getSala().getTavolo(i);
            PannelloTavolo pannelloTavolo = salaPanel.getPannelloTavolo(i);

            Timer timer = new Timer(1000, null);
            TimerTavolo timerTavolo = new TimerTavolo(tavolo, salaPanel, pannelloTavolo, gioco, controllerNotifiche, controllerNavigazione, this, timer);

            timer.addActionListener(timerTavolo);

            timerTavoli.add(timer);
        }
    }

    public void initializzaTimerCuochi(SalaPanel salaPanel, CucinaPanel cucinaPanel) {
        for (int i = 1; i <= Gioco.NUM_CUOCHI; i++) {
            PannelloCuoco pannelloCuoco = cucinaPanel.getPannelloCuoco(i);
            Cuoco cuoco = gioco.getCuoco(i);

            Timer timer = new Timer(1000, null);
            TimerCuoco timerCuoco = new TimerCuoco(cuoco, pannelloCuoco, salaPanel, gioco, controllerNotifiche, timer);

            timer.addActionListener(timerCuoco);

            timerCuochi.add(timer);
        }
    }

    public Timer getTimerCuoco(int numeroCuoco) {
        return timerCuochi.get(numeroCuoco - 1);
    }

    public Timer getTimerTavolo(int numeroTavolo) {
        return timerTavoli.get(numeroTavolo - 1);
    }
}
