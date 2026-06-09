package it.gennystabile.ricettariobe.dto.ricetta;

public class RicettaCardOutputDto {
    private Long id;
    private String titolo;
    private String imgPrincipale;
    private Float difficolta;
    private Integer votiTotali;
    private Float valutazioneMedia;
    private Short tempoDiPreparazione;

    public Float getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(Float difficolta) {
        this.difficolta = difficolta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getImgPrincipale() {
        return imgPrincipale;
    }

    public void setImgPrincipale(String imgPrincipale) {
        this.imgPrincipale = imgPrincipale;
    }

    public Integer getVotiTotali() {
        return votiTotali;
    }

    public void setVotiTotali(Integer votiTotali) {
        this.votiTotali = votiTotali;
    }

    public Float getValutazioneMedia() {
        return valutazioneMedia;
    }

    public void setValutazioneMedia(Float valutazioneMedia) {
        this.valutazioneMedia = valutazioneMedia;
    }

    public Short getTempoDiPreparazione() {
        return tempoDiPreparazione;
    }

    public void setTempoDiPreparazione(Short tempoDiPreparazione) {
        this.tempoDiPreparazione = tempoDiPreparazione;
    }
}
