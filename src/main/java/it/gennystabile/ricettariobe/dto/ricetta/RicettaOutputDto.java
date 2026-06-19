package it.gennystabile.ricettariobe.dto.ricetta;

import it.gennystabile.ricettariobe.dto.multimedia.MultimediaOutputDto;
import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;
import it.gennystabile.ricettariobe.dto.ricetta.composizionericetta.ComposizioneRicettaOutputDto;

import java.util.List;

public class RicettaOutputDto {

    private Long id;
    private String titolo;
    private Float difficolta;
    private Short tempoDiPreparazione;
    private String imgPrincipale;
    private Integer votiTotali;
    private Float valutazioneMedia;
    private String descBreve;
    private List<CategoriaOutputDto> categorie;
    private List<ComposizioneRicettaOutputDto> composizioneRicetta;
    private List<MultimediaOutputDto> multimedias;

    public String getDescBreve() {
        return descBreve;
    }

    public void setDescBreve(String descBreve) {
        this.descBreve = descBreve;
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

    public List<MultimediaOutputDto> getMultimedias() {
        return multimedias;
    }

    public void setMultimedias(List<MultimediaOutputDto> multimedias) {
        this.multimedias = multimedias;
    }

    public Float getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(Float difficolta) {
        this.difficolta = difficolta;
    }

    public Short getTempoDiPreparazione() {
        return tempoDiPreparazione;
    }

    public void setTempoDiPreparazione(Short tempoDiPreparazione) {
        this.tempoDiPreparazione = tempoDiPreparazione;
    }

    public List<ComposizioneRicettaOutputDto> getComposizioneRicetta() {
        return composizioneRicetta;
    }

    public void setComposizioneRicetta(List<ComposizioneRicettaOutputDto> composizioneRicetta) {
        this.composizioneRicetta = composizioneRicetta;
    }

    public List<CategoriaOutputDto> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<CategoriaOutputDto> categorie) {
        this.categorie = categorie;
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
}
