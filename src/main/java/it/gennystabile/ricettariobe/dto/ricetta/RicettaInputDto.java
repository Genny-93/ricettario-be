package it.gennystabile.ricettariobe.dto.ricetta;

import it.gennystabile.ricettariobe.dto.multimedia.MultimediaInputDto;
import it.gennystabile.ricettariobe.dto.ricetta.composizionericetta.ComposizioneRicettaInputDto;
import it.gennystabile.ricettariobe.model.CategoriaIngrediente;
import it.gennystabile.ricettariobe.model.FaseProcedimento;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class RicettaInputDto {

    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    @NotNull
    @Min(value = 1, message = "Il tempo di preparazione deve essere di almeno 1 minuto")
    private Short tempoDiPreparazione;

    @NotNull(message = "La difficoltà è obbligatoria")
    @DecimalMin(value = "0.5", message = "La difficoltà non può essere inferiore a 0.5")
    @DecimalMax(value = "5.0", message = "La difficoltà non può essere superiore a 5.0")
    private Float difficolta;

    @NotEmpty
    private List<FaseProcedimento> procedimento;

    @NotBlank
    private String descBreve;

    @NotEmpty(message = "Inserire almeno una categoria")
    private List<String> categorieRicetta;

    private List<MultimediaInputDto> listaMultimedia;

    @NotEmpty(message = "La composizione della ricetta non può essere vuota")
    private List<@Valid ComposizioneRicettaInputDto> composizioneRicetta;

    public List<ComposizioneRicettaInputDto> getComposizioneRicetta() {
        return composizioneRicetta;
    }

    public void setComposizioneRicetta(List<ComposizioneRicettaInputDto> composizioneRicetta) {
        this.composizioneRicetta = composizioneRicetta;
    }

    public List<String> getCategorieRicetta() {
        return categorieRicetta;
    }

    public void setCategorieRicetta(List<String> categorieRicetta) {
        this.categorieRicetta = categorieRicetta;
    }

    public List<MultimediaInputDto> getListaMultimedia() {
        return listaMultimedia;
    }

    public void setListaMultimedia(List<MultimediaInputDto> listaMultimedia) {
        this.listaMultimedia = listaMultimedia;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Short getTempoDiPreparazione() {
        return tempoDiPreparazione;
    }

    public void setTempoDiPreparazione(Short tempoDiPreparazione) {
        this.tempoDiPreparazione = tempoDiPreparazione;
    }

    public Float getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(Float difficolta) {
        this.difficolta = difficolta;
    }

    public @NotEmpty List<FaseProcedimento> getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(@NotEmpty List<FaseProcedimento> procedimento) {
        this.procedimento = procedimento;
    }

    public String getDescBreve() {
        return descBreve;
    }

    public void setDescBreve(String descBreve) {
        this.descBreve = descBreve;
    }
}
