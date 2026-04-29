package restaurantsim.model;

public class Notifica {
    private String testo;
    private String origine;

    public Notifica(String testo, String origine) {
        this.testo = testo;
        this.origine = origine;
    }

    public String getTesto() {
        return testo;
    }

    public String getOrigine() {
        return origine;
    }
}
