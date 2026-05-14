package restaurantsim.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;

import restaurantsim.model.Gioco;
import restaurantsim.model.Notifica;
import restaurantsim.model.Piatto;
import restaurantsim.model.PiattoErratoException;
import restaurantsim.model.StatoTavolo;
import restaurantsim.model.Tavolo;
import restaurantsim.model.TavoloNonOccupatoException;
import restaurantsim.model.TransferPiatto;
import restaurantsim.view.PannelloTavolo;
import restaurantsim.view.PannelloSala;

/**
 * Handle che gestisce la logica del drag and drop (DnD) dei piatti dalla sala ai tavoli. Viene usato sia per esportare un piatto (drag) che per importare un piatto (rilascio).
 */
public class PiattoTransferHandle extends TransferHandler {
	/** Serial Version UID */
	private static final long serialVersionUID = 1L;
	/**
	 * Il DataFlavor usato per identificare il trasferimento di un piatto. Il tipo di dato trasferito è un oggetto TransferPiatto.
	 */
	public static final DataFlavor PIATTO_DATA_FLAVOR = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + TransferPiatto.class.getName(), "Piatto");

	/**
	 * Dati del piatto da trasferire.
	 * 
	 * Presente solo se questo handle ha lo scopo di esportare.
	 */
	private TransferPiatto transferPiatto;

	/**
	 * Gioco a cui questo handle è associato.
	 * 
	 * Presenti solo se questo handle ha lo scopo di importare.
	 */
	private Gioco gioco;
	/**
	 * Tavolo a cui questo handle è associato.
	 * 
	 * Presenti solo se questo handle ha lo scopo di importare.
	 */
	private Tavolo tavolo;
	/**
	 * Pannello per aggiornare la UI della sala dopo l'importazione del piatto.
	 * 
	 * Presenti solo se questo handle ha lo scopo di importare.
	 */
	private PannelloSala pannelloSala;
	/**
	 * Pannello per aggiornare la UI del tavolo dopo l'importazione del piatto.
	 * 
	 * Presenti solo se questo handle ha lo scopo di importare.
	 */
	private PannelloTavolo pannelloTavolo;
	/**
	 * Controller per mostrare le notifiche dopo l'importazione del piatto.
	 * 
	 * Presenti solo se questo handle ha lo scopo di importare.
	 */
	private ControllerNotifiche controllerNotifiche;
	/**
	 * 
	 */
	private ControllerPartita controllerPartita;

	/**
	 * Crea un PiattoTransferHandle configurato per esportare un piatto (drag)
	 *
	 * @param piatto Il piatto da trasferire
	 */
	public PiattoTransferHandle(TransferPiatto piatto) {
		this.transferPiatto = piatto;
		this.gioco = null;
		this.tavolo = null;
		this.pannelloSala = null;
		this.pannelloTavolo = null;
		this.controllerNotifiche = null;
		this.controllerPartita = null;
	}

	/**
	 * Crea un PiattoTransferHandle configurato per importare un piatto (rilascio)
	 * 
	 * @param gioco               Il gioco a cui questo handle è associato
	 * @param tavolo              Il tavolo a cui questo handle è associato
	 * @param pannelloSala        Il pannello della sala
	 * @param pannelloTavolo      Il pannello del tavolo
	 * @param controllerNotifiche Il controller delle notifiche
	 * @param controllerPartita   Il controller della partita
	 */
	public PiattoTransferHandle(Gioco gioco, Tavolo tavolo, PannelloSala pannelloSala, PannelloTavolo pannelloTavolo, ControllerNotifiche controllerNotifiche, ControllerPartita controllerPartita) {
		this.transferPiatto = null;
		this.gioco = gioco;
		this.tavolo = tavolo;
		this.pannelloSala = pannelloSala;
		this.pannelloTavolo = pannelloTavolo;
		this.controllerNotifiche = controllerNotifiche;
		this.controllerPartita = controllerPartita;
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
	/**
	 * Crea un instanza del {@link PiattoTransferable} per esportare il piatto associato a questo handle.
	 * 
	 * @param c Il componente da cui viene iniziato il drag - Non utilizzato
	 */
	@Override
	protected Transferable createTransferable(JComponent c) {
		return new PiattoTransferable(transferPiatto);
	}

	/**
	 * Determina l'azione che deve essere compiuta al Piatto quando viene trasciato.
	 * 
	 * Se non ce un piatto per questo handle il drag viene disabiliato.
	 */
	@Override
	public int getSourceActions(JComponent c) {
		// NONE disabilita il drag
		return isPiattoPresente() ? MOVE : NONE;
	}

	/// Import del piatto - Rilascio
	/**
	 * Controllo se il trasferimento in corso è compatibile con questo handle.
	 * 
	 * Per essere compatibile il trasferimento deve avere solo il DataFlavor PIATTO_DATA_FLAVOR e questo handle non deve avere già un piatto.
	 */
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

	/**
	 * Importa il piatto trasferito e aggiorna la UI.
	 */
	@Override
	public boolean importData(TransferSupport support) {
		Transferable transferable = support.getTransferable();

		try {
			TransferPiatto transferPiatto = (TransferPiatto) transferable.getTransferData(PIATTO_DATA_FLAVOR);
			processaRilascio(transferPiatto);
		} catch (UnsupportedFlavorException e) {
			JOptionPane.showMessageDialog(pannelloTavolo, "Errore durante il trasferimento del piatto: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(pannelloTavolo, "Errore durante il trasferimento del piatto: " + e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (InterruptedException e) {
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
	 * 
	 * @param gioco               Il gioco a cui associare i controller dei cuochi
	 * @param salaPanel           Il pannello della sala a cui associare i controller dei cuochi
	 * @param controllerNotifiche Il controller delle notifiche a cui associare i controller dei cuochi
	 * @param controllerPartita   Il controller della partita a cui associare i controller dei cuochi
	 */
	public static void registraTransferHandles(final Gioco gioco, final PannelloSala salaPanel, final ControllerNotifiche controllerNotifiche, final ControllerPartita controllerPartita) {
		salaPanel.registraTransferHandlerPiatto(new restaurantsim.view.Function<PannelloTavolo, PiattoTransferHandle>() {
			@Override
			public PiattoTransferHandle apply(PannelloTavolo panel) {
				Tavolo tavolo = gioco.getSala().getTavolo(panel.getNumeroTavolo());
				return new PiattoTransferHandle(gioco, tavolo, salaPanel, panel, controllerNotifiche, controllerPartita);
			}
		});
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
			StatoTavolo statoTavolo = tavolo.serviTavolo(piatto);

			// Interrompi il timer del tavolo in quanto non più necessario.
			controllerPartita.getTimerTavolo(numTavolo).stop();

			gioco.getSala().rimuoviPiatto(indexPiatto);
			gioco.aggiungiPunteggio(Gioco.PUNTEGGIO_PER_PIATTO);

			pannelloSala.aggiornaPunteggio(gioco.getPunteggio());
			pannelloSala.aggiornaBancone(gioco.getSala().getPiattiPronti());

			Notifica notifica = new Notifica("Tavolo " + numTavolo + " ha ricevuto " + piatto.toString(), ControllerNotifiche.ORIGINE_SALA);
			gioco.registraNotifica(notifica);
			controllerNotifiche.mostraNotifica(notifica);

			pannelloTavolo.aggiornaTavolo(statoTavolo);

			// Disolito i revalidate li fa Swing dopo un evento, ma qua non lo fa, quindi dobbiamo farlo a mano per aggiornare la UI dopo averla modificata
			pannelloSala.revalidate();
			pannelloSala.repaint();

			pannelloTavolo.revalidate();
			pannelloTavolo.repaint();
		} catch (TavoloNonOccupatoException tnoe) {
			JOptionPane.showMessageDialog(pannelloSala, "Non c'è nessuno a questo tavolo!", "Tavolo vuoto.", JOptionPane.ERROR_MESSAGE);
		} catch (PiattoErratoException pee) {
			JOptionPane.showMessageDialog(pannelloSala, pee.getMessage(), "Piatto errato.", JOptionPane.ERROR_MESSAGE);
		}
	}
}
