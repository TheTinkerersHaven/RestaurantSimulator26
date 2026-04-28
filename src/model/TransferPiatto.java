package model;

public class TransferPiatto {
    private Piatto piatto;
    private int indexPiatto;

    public TransferPiatto(Piatto piatto, int indexPiatto) {
        this.piatto = piatto;
        this.indexPiatto = indexPiatto;
    }

    public Piatto getPiatto() {
        return piatto;
    }

    public int getIndexPiatto() {
        return indexPiatto;
    }
}
