package restaurantsim.controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import restaurantsim.view.Finestra;

/**
 * Controller che si occupa di gestire la chiusura della finestra.
 */
public class ControllerFinestra implements WindowListener {
    /**
     * La finistra da gestire
     */
    private Finestra finestra;
    /**
     * Il controller della partita, usato per poter fermare la partita quando si chiude la finestra.
     */
    private ControllerPartita controllerPartita;

    /**
     * Inizialliza il controller della finistra
     * 
     * Registra anche il controller con la finistra.
     * 
     * @param finestra          La finestra da gestire
     * @param controllerPartita Il controller della partita, usato per poter fermare la partita quando si chiude la finestra.
     */
    public ControllerFinestra(Finestra finestra, ControllerPartita controllerPartita) {
        this.finestra = finestra;
        this.controllerPartita = controllerPartita;
        finestra.addWindowListener(this);
    }

    /** {@inheritDoc} */
    @Override
    public void windowOpened(WindowEvent e) {
        // Non usato.
    }

    /**
     * Quando si chiude la finestra, mostra una finestra di conferma, se l'utente conferma, chiude la finestra.
     */
    @Override
    public void windowClosing(WindowEvent e) {
        chiudiFinestraConConferma();
    }

    /** {@inheritDoc} */
    @Override
    public void windowClosed(WindowEvent e) {
        // Non usato.
    }

    /** {@inheritDoc} */
    @Override
    public void windowIconified(WindowEvent e) {
        // Non usato.
    }

    /** {@inheritDoc} */
    @Override
    public void windowDeiconified(WindowEvent e) {
        // Non usato.
    }

    /** {@inheritDoc} */
    @Override
    public void windowActivated(WindowEvent e) {
        // Non usato.
    }

    /** {@inheritDoc} */
    @Override
    public void windowDeactivated(WindowEvent e) {
        // Non usato.
    }

    /**
     * Mostra una finestra di conferma per chiudere la finestra, se l'utente conferma, chiude la finestra.
     */
    public void chiudiFinestraConConferma() {
        int esito = JOptionPane.showConfirmDialog(finestra, "Sei sicuro di voler uscire?", "Conferma uscita", JOptionPane.YES_NO_OPTION);
        if (esito == JOptionPane.YES_OPTION) {
            controllerPartita.finisciPartita(ControllerPartita.ESCI_SENZA_MESSAGGIO);
            // Permetti a Swing/AWT di terminare l'applicazione, dato che non rimaranno thread attivi la JVM terminerà da sola.
            // https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/doc-files/AWTThreadIssues.html
            finestra.dispose();
        }
    }
}
