package restaurantsim.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.type.TypeReference;

import restaurantsim.model.Classifica;
import restaurantsim.model.Cuoco;
import restaurantsim.model.Gioco;
import restaurantsim.model.Piatto;
import restaurantsim.model.Salvataggio;
import restaurantsim.model.StatoTavolo;
import restaurantsim.model.Tavolo;
import restaurantsim.view.ClassificaPanel;
import restaurantsim.view.PannelloCucina;
import restaurantsim.view.PannelloPrincipale;
import restaurantsim.view.PannelloCuoco;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.PannelloSala;

/**
 * Controller che si occupa di gestire la partita
 */
public class ControllerPartita {
    /**
     * Gioco a cui si riferisce il controller
     */
    private Gioco gioco;
    /**
     * Classifica a cui si riferisce il controller per poter aggiornare la classifica quando necessario
     */
    private Classifica classifica;
    /**
     * Riferimento al pannello principale per poter aggiornare la view quando necessario
     */
    private PannelloPrincipale mainPanel;
    /**
     * Riferimento al controller notifiche per poter aggiornare le notifiche quando necessario
     */
    private ControllerNotifiche controllerNotifiche;
    /**
	 * Controller per gestire i suoni.
	 */
    private ControllerSuoni controllerSuoni;
    /**
     * Timer per i tavoli
     */
    private ArrayList<Timer> timerTavoli;
    /**
     * Timer per i cuochi
     */
    private ArrayList<Timer> timerCuochi;
    /**
     * SwingWorker che si occupa di far arrivare i clienti
     */
    private ArrivoClientiWorker arrivoClientiWorker;

    // Delle constanti per rendere più chiaro il fine partita
    /** Esci dalla partita senza sconfitta e senza chiedere il nome dell'utente */
    public static final int ESCI_SENZA_MESSAGGIO = 0;
    /** Esci dalla partita chiedendo solo il nome dell'utente */
    public static final int ESCI_SOLO_NOME = 1;
    /** Esci dalla partita informando della sconditta e chiedend il nome dell'utente */
    public static final int ESCI_NOME_E_SCONFITTA = 2;

    /**
     * Inizializza il controller partita.
     * 
     * @param panel               il pannello principale, necessario per aggiornare la view quando necessario
     * @param gioco               il gioco, necessario per aggiornare il model quando necessario
     * @param classifica          la classifica, necessaria per aggiornare la classifica quando necessario
     * @param controllerNotifiche il controller notifiche, necessario per aggiornare le notifiche quando necessario
     * @param controllerSuoni     il controller dei suoni, necessario per riprodurre gli effetti sonori
     */
    public ControllerPartita(PannelloPrincipale panel, Gioco gioco, Classifica classifica, ControllerNotifiche controllerNotifiche, ControllerSuoni controllerSuoni) {
        this.mainPanel = panel;
        this.gioco = gioco;
        this.classifica = classifica;
        this.controllerNotifiche = controllerNotifiche;
        this.controllerSuoni = controllerSuoni;

        this.timerTavoli = new ArrayList<>(Gioco.NUM_TAVOLI);
        this.timerCuochi = new ArrayList<>(Gioco.NUM_CUOCHI);
        this.arrivoClientiWorker = new ArrivoClientiWorker(gioco, panel.getSalaPanel(), controllerNotifiche, this, controllerSuoni);
    }

    /**
     * Inizia una nuova partita, facendo partire il worker che fa arrivare i clienti
     */
    public void nuovaParita() {
        if (arrivoClientiWorker != null && !arrivoClientiWorker.isDone()) {
            arrivoClientiWorker.cancel(true);
        }
        this.arrivoClientiWorker = new ArrivoClientiWorker(gioco, mainPanel.getSalaPanel(), controllerNotifiche, this, controllerSuoni);
        arrivoClientiWorker.execute();
    }

    /**
     * Finisce la partita.
     * 
     * @param status il tipo di fine partita, che determina se mostrare un messaggio di sconfitta e se chiedere il nome dell'utente per aggiornare la classifica, deve essere uno dei valori delle costanti {@link #ESCI_SENZA_MESSAGGIO}, {@link #ESCI_SOLO_NOME} o {@link #ESCI_NOME_E_SCONFITTA}
     * @throws InterruptedException se il thread viene interrotto durante il reset della partita.
     */
    public void finisciPartita(int status) throws InterruptedException {
        interrompiPartita();

        if (status == ESCI_SOLO_NOME || status == ESCI_NOME_E_SCONFITTA) {
            ClassificaPanel classificaPanel = mainPanel.getClassificaPanel();
            if (status == ESCI_NOME_E_SCONFITTA)
                JOptionPane.showMessageDialog(mainPanel, "Gioco terminato! Hai fatto arrabbiare troppi clienti!", "Partita finita", JOptionPane.INFORMATION_MESSAGE);
            String nomeGiocatore;
            do {
                nomeGiocatore = JOptionPane.showInputDialog(mainPanel, "Inserisci il nome del giocatore.", "Inserisci nome", JOptionPane.INFORMATION_MESSAGE);
                if (nomeGiocatore == null || nomeGiocatore.isBlank())
                    JOptionPane.showMessageDialog(mainPanel, "Non puoi non inserire un nome.", "Errore", JOptionPane.OK_OPTION);
            } while (nomeGiocatore == null || nomeGiocatore.isBlank());
            classifica.inserisciPartita(nomeGiocatore, gioco.getPunteggio());
            classificaPanel.aggiornaClassifica(classifica.getClassifica());

            ObjectMapper mapper = new ObjectMapper();
            File file = new File("classifica.json");

            try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, classifica.getClassifica());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Errore nel salvataggio della classifica.", "Errore salvataggio", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }

        /// Model reset
        gioco.reset();
        controllerNotifiche.pulisciTutto();

        /// View reset
        aggiornaView();
    }

    /**
     * Aggiorna tutti i pannelli della view basandosi sullo stato attuale del modello Gioco.
     * 
     * @throws InterruptedException se il thread viene interrotto durante il recupero dello stato dei tavoli.
     */
    private void aggiornaView() throws InterruptedException {
        // Notifiche
        mainPanel.aggiornaMenuNotifiche(gioco.getNotifiche(), controllerNotifiche);

        // Sala
        PannelloSala salaPanel = mainPanel.getSalaPanel();
        salaPanel.aggiornaPunteggio(gioco.getPunteggio());
        salaPanel.aggiornaBancone(gioco.getSala().getPiattiPronti());
        for (int i = 1; i <= Gioco.NUM_TAVOLI; i++) {
            Tavolo tavolo = gioco.getSala().getTavolo(i);
            StatoTavolo statoTavolo = tavolo.getStatoTavolo();

            salaPanel.getPannelloTavolo(i).aggiornaTavolo(statoTavolo);
        }

        // Cucina
        PannelloCucina cucinaPanel = mainPanel.getCucinaPanel();
        for (int i = 1; i <= Gioco.NUM_CUOCHI; i++) {
            PannelloCuoco pannelloCuoco = cucinaPanel.getPannelloCuoco(i);
            Cuoco cuoco = this.gioco.getCuoco(i);

            if (cuoco.getPiattoInPreparazione().equals(Piatto.NESSUNO)) {
                pannelloCuoco.aggiornaProgresso(0);
                pannelloCuoco.rimuoviImmagine();
            } else {
                double progresso = ((double) cuoco.getTempoRimanente()) / cuoco.getPiattoInPreparazione().getTempoDiPreparazione();
                pannelloCuoco.aggiornaProgresso(100 - (int) (progresso * 100));
                pannelloCuoco.mostraPiatto(cuoco.getPiattoInPreparazione().getImmaginePiatto());
            }
        }
    }

    /**
     * Interrompe la partita, fermando i timer dei tavoli e dei cuochi e cancellando il worker che fa arrivare i clienti
     */
    private void interrompiPartita() {
        arrivoClientiWorker.cancel(true);

        for (Timer timer : timerTavoli) {
            timer.stop();
        }

        for (Timer timer : timerCuochi) {
            timer.stop();
        }
    }

    /**
     * Inizializza i timer dei tavoli.
     * 
     * @param salaPanel             il pannello della sala, necessario per aggiornare il pannello del tavolo
     * @param controllerNavigazione il controller di navigazione
     */
    public void initializzaTimerTavoli(PannelloSala salaPanel, ControllerNavigazione controllerNavigazione) {
        for (int i = 1; i <= Gioco.NUM_TAVOLI; i++) {
            Tavolo tavolo = gioco.getSala().getTavolo(i);
            PannelloTavolo pannelloTavolo = salaPanel.getPannelloTavolo(i);

            Timer timer = new Timer(TimerTavolo.INTERVALLO, null);
            TimerTavolo timerTavolo = new TimerTavolo(gioco, tavolo, salaPanel, pannelloTavolo, controllerNotifiche, controllerNavigazione, this, timer);

            timer.addActionListener(timerTavolo);

            timerTavoli.add(timer);
        }
    }

    /**
     * Inizializza i timer dei cuochi.
     * 
     * @param salaPanel   il pannello della sala, necessario per aggiornare la sala quando un cuoco finisce di preparare un piatto e deve essere spostato al bancone
     * @param cucinaPanel il pannello della cucina, necessario per aggiornare il pannello del cuoco
     */
    public void initializzaTimerCuochi(PannelloSala salaPanel, PannelloCucina cucinaPanel) {
        for (int i = 1; i <= Gioco.NUM_CUOCHI; i++) {
            PannelloCuoco pannelloCuoco = cucinaPanel.getPannelloCuoco(i);
            Cuoco cuoco = gioco.getCuoco(i);

            Timer timer = new Timer(TimerCuoco.INTERVALLO, null);
            TimerCuoco timerCuoco = new TimerCuoco(gioco, cuoco, pannelloCuoco, salaPanel, controllerNotifiche, controllerSuoni, timer);

            timer.addActionListener(timerCuoco);

            timerCuochi.add(timer);
        }
    }

    /**
     * Restituisce il timer del cuoco con il numero specificato
     * 
     * @param numeroCuoco il numero del cuoco di cui si vuole ottenere il timer
     * @return il timer del cuoco con il numero specificato
     */
    public Timer getTimerCuoco(int numeroCuoco) {
        return timerCuochi.get(numeroCuoco - 1);
    }

    /**
     * Restituisce il timer del tavolo con il numero specificato
     * 
     * @param numeroTavolo il numero del tavolo di cui si vuole ottenere il timer
     * @return il timer del tavolo con il numero specificato
     */
    public Timer getTimerTavolo(int numeroTavolo) {
        return timerTavoli.get(numeroTavolo - 1);
    }

    /**
     * Salva lo stato attuale della partita su un file JSON.
     * 
     * @throws StreamWriteException se si verifica un errore durante la scrittura dello stream
     * @throws DatabindException    se si verifica un errore durante il binding dei dati
     * @throws IOException          se si verifica un errore di I/O
     * @throws InterruptedException se il thread viene interrotto durante il recupero dello stato dei tavoli nel modello Salvataggio.
     */
    public void salvaPartita() throws StreamWriteException, DatabindException, IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        Salvataggio salvataggio = new Salvataggio(gioco);
        File file = new File("salvataggio.json");

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, salvataggio);
    }

    /**
     * Carica lo stato della partita da un file JSON e riavvia i timer necessari.
     * 
     * @throws StreamReadException  se si verifica un errore durante la lettura dello stream
     * @throws DatabindException    se si verifica un errore durante il binding dei dati
     * @throws IOException          se si verifica un errore di I/O
     * @throws InterruptedException se il thread viene interrotto durante l'aggiornamento della view o il caricamento dei dati nel modello.
     */
    public void caricaPartita() throws StreamReadException, DatabindException, IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("salvataggio.json");

        Salvataggio salvataggio = mapper.readValue(file, Salvataggio.class);

        gioco.carica(salvataggio);

        aggiornaView();

        if (arrivoClientiWorker != null && !arrivoClientiWorker.isDone()) {
            arrivoClientiWorker.cancel(true);
        }
        this.arrivoClientiWorker = new ArrivoClientiWorker(gioco, mainPanel.getSalaPanel(), controllerNotifiche, this, controllerSuoni);
        this.arrivoClientiWorker.execute();

        for (int i = 0; i < Gioco.NUM_CUOCHI; i++) {
            if (!this.gioco.getCuoco(i + 1).getPiattoInPreparazione().equals(Piatto.NESSUNO)) {
                this.timerCuochi.get(i).start();
            }
        }
        for (int i = 0; i < Gioco.NUM_TAVOLI; i++) {
            if (this.gioco.getSala().getTavolo(i + 1).isOccupato()) {
                this.timerTavoli.get(i).start();
            }
        }
    }

    /**
     * Carica la classifica dal file JSON.
     */
    public void caricaClassifica() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("classifica.json");

        if (!file.exists()) {
            return;
        }

        try {
            ArrayList<String> classificaList = mapper.readValue(file, new TypeReference<ArrayList<String>>() {
            });
            classifica.pulisciClassifica();
            classifica.aggiungiTutto(classificaList);
            mainPanel.getClassificaPanel().aggiornaClassifica(classifica.getClassifica());
        } catch (IOException e) {
            System.err.println("Errore nel caricamento della classifica");
            e.printStackTrace();
        }
    }

    /**
     * Elimina il file della classifica e pulisce la lista dei punteggi nel modello e nella view.
     */
    public void pulisciClassifica() {
        File file = new File("classifica.json");
        file.delete();
        classifica.pulisciClassifica();
        mainPanel.getClassificaPanel().aggiornaClassifica(classifica.getClassifica());
    }

    /**
     * Elimina il file di salvataggio della partita.
     */
    public void eliminaSalvataggio() {
        File file = new File("salvataggio.json");
        file.delete();
    }

    /**
     * Restituisce il controller dei suoni.
     * 
     * @return il controller dei suoni
     */
    public ControllerSuoni getControllerSuoni() {
        return this.controllerSuoni;
    }

    /**
     * Restituisce il controller delle notifiche.
     * 
     * @return il controller delle notifiche
     */
    public ControllerNotifiche getControllerNotifiche() {
        return this.controllerNotifiche;
    }
}
