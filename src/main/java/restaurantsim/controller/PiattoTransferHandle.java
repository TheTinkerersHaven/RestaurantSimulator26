package restaurantsim.controller;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

import restaurantsim.model.Piatto;
import restaurantsim.model.Sala;
import restaurantsim.model.Tavolo;
import restaurantsim.model.TransferPiatto;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.SalaPanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

public class PiattoTransferHandle extends TransferHandler {
    /**
     * Il DataFlavor usato per identificare il trasferimento di un piatto. Il tipo di dato trasferito è un oggetto TransferPiatto.
     */
    public static final DataFlavor PIATTO_DATA_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TransferPiatto.class.getName(), "Piatto");

    private TransferPiatto transferPiatto;

    private Sala sala;
    private Tavolo tavolo;
    private SalaPanel salaPanel;
    private PannelloTavolo pannelloTavolo;

    /**
     * Crea un PiattoTransferHandle configurato per esportare un piatto (drag)
     */
    public PiattoTransferHandle(TransferPiatto piatto) {
        this.transferPiatto = piatto;
        this.sala = null;
        this.tavolo = null;
        this.salaPanel = null;
        this.pannelloTavolo = null;
    }

    /**
     * Crea un PiattoTransferHandle configurato per importare un piatto (rilascio)
     */
    public PiattoTransferHandle(Sala sala, Tavolo tavolo, SalaPanel salaPanel, PannelloTavolo pannelloTavolo) {
        this.transferPiatto = null;
        this.sala = sala;
        this.tavolo = tavolo;
        this.salaPanel = salaPanel;
        this.pannelloTavolo = pannelloTavolo;
    }

    /**
     * Usiamo transferPiatto per discriminare se questo TransferHandle deve permettere di trasciare un piatto (se transferPiatto è valorizzato) o di rilasciare un piatto (se transferPiatto è null).
     *
     * In questo modo possiamo usare la stessa classe sia per il drag che per il drop, invece di doverne creare due diverse.
     */
    private boolean isPiattoPresente() {
        return transferPiatto != null;
    }

    /// Export del piatto - Drag
    @Override
    protected Transferable createTransferable(JComponent c) {
        return new PiattoTransferable(transferPiatto);
    }

    @Override
    public int getSourceActions(JComponent c) {
        // NONE disabilita il drag
        return isPiattoPresente() ? MOVE : NONE;
    }

    /// Import del piatto - Rilascio
    @Override
    public boolean canImport(TransferSupport support) {
        if (isPiattoPresente()) {
            // Se c'è già un piatto, non permettere di importarne un altro
            return false;
        }

        // Check that it supports only the PIATTO_DATA_FLAVOR
        if (support.getDataFlavors().length != 1)
            return false;
        return support.isDataFlavorSupported(PIATTO_DATA_FLAVOR);
    }

    @Override
    public boolean importData(TransferSupport support) {
        Transferable transferable = support.getTransferable();

        try {
            TransferPiatto transferPiatto = (TransferPiatto) transferable.getTransferData(PIATTO_DATA_FLAVOR);
            processaRilascio(transferPiatto);
        } catch (UnsupportedFlavorException | IOException | InterruptedException e) {
            JOptionPane.showMessageDialog(pannelloTavolo, "Errore durante il trasferimento del piatto: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (RuntimeException ex) {
            // Swing nasconde tutte le eccezioni lanciate da importData, per il debug però è utile sapere se ce un eccezzione
            ex.printStackTrace();
            throw ex;
        }

        return true;
    }

    /**
     * Registra i controller dei cuochi al mainPanel Crea un nuovo cuoco per ogni pannelloCuoco che verrà creato
     */
    public static void registraTrasnferHandles(Sala sala, SalaPanel salaPanel) {
        salaPanel.registraTransferHandlerPiatto(panel -> new PiattoTransferHandle(sala, sala.getTavolo(panel.getNumeroTavolo()), salaPanel, panel));
    }

    /**
     * Esegui la logica di rilascio del piatto sul tavolo associato a questo TransferHandler. In questo caso, mostra un messaggio di successo.
     *
     * Metodo separato per separare la logica di importazione dal Transferable dal lavoro relativo al tavolo.
     */
    private void processaRilascio(TransferPiatto transferPiatto) throws InterruptedException {
        Piatto piatto = transferPiatto.getPiatto();
        int indexPiatto = transferPiatto.getIndexPiatto();
        int numTavolo = tavolo.getNumeroTavolo();

        try {
            tavolo.serviTavolo(piatto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pannelloTavolo, e.getMessage(), "Piatto errato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        sala.rimuoviPiatto(indexPiatto);

        salaPanel.aggiornaBancone(sala.getPiattiPronti());

        pannelloTavolo.aggiornaTavolo(tavolo);

        // Disolito i revalidate li fa Swing dopo un evento, ma qua non lo fa, quindi dobbiamo farlo a mano per aggiornare la UI dopo averla modificata
        salaPanel.revalidate();
        salaPanel.repaint();

        pannelloTavolo.revalidate();
        pannelloTavolo.repaint();

        JOptionPane.showMessageDialog(salaPanel, "Piatto " + piatto + " servito al tavolo al tavolo " + numTavolo + "!", "Successo", JOptionPane.INFORMATION_MESSAGE);
    }
}
