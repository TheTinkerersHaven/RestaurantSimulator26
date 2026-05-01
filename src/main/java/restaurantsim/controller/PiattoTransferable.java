package restaurantsim.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import restaurantsim.model.TransferPiatto;

/**
 * Classe che conserva i dati e i metadati per un piatto quando viene effettuata un operazione di drag and drop (DnD) per un piatto.
 */
public class PiattoTransferable implements Transferable {
	/**
	 * Il piatto da trasferire durante l'operazione di drag and drop.
	 */
	private TransferPiatto transferPiatto;

	/**
	 * Crea un nuovo oggetto PiattoTransferable con il piatto da trasferire.
	 * 
	 * @param transferPiatto Il piatto da trasferire durante l'operazione di drag and drop.
	 */
	public PiattoTransferable(TransferPiatto transferPiatto) {
		this.transferPiatto = transferPiatto;
	}

	/** {@inheritDoc} */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { PiattoTransferHandle.PIATTO_DATA_FLAVOR };
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(PiattoTransferHandle.PIATTO_DATA_FLAVOR);
	}

	/** {@inheritDoc} */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return transferPiatto;
	}
}
