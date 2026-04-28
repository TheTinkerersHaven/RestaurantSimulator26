package controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import model.TransferPiatto;

public class PiattoTransferable implements Transferable {
    private TransferPiatto transferPiatto;

    public PiattoTransferable(TransferPiatto transferPiatto) {
        this.transferPiatto = transferPiatto;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { PiattoTransferHandle.PIATTO_DATA_FLAVOR };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(PiattoTransferHandle.PIATTO_DATA_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return transferPiatto;
    }
}
