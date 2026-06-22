package it.gennystabile.ricettariobe.model;

//CLASSE POJO
public class FaseProcedimento {
    private String testo;
    private String media;

    public FaseProcedimento() {
    }

    public FaseProcedimento(String testo, String media) {
        this.testo = testo;
        this.media = media;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
