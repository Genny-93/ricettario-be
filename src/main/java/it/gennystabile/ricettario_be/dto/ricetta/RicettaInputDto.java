package it.gennystabile.ricettario_be.dto.ricetta;

import it.gennystabile.ricettario_be.dto.multimedia.MultimediaInputDto;
import it.gennystabile.ricettario_be.model.Multimedia;
import jakarta.validation.constraints.*;

import java.util.List;

public class RicettaInputDto {

    @NotBlank
    private String titolo;

    @NotNull
    private Short tempoDiPreparazione;

    @DecimalMin(value = "0.5", message = "La difficoltà non può essere inferiore a 0.5")
    @DecimalMax(value = "5.0", message = "La difficoltà non può essere superiore a 5.0")
    private Float difficolta;

    @NotBlank
    private String procedimento;

    @NotBlank
    private String descBreve;

    private List<MultimediaInputDto> listaMultimedia;

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

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public String getDescBreve() {
        return descBreve;
    }

    public void setDescBreve(String descBreve) {
        this.descBreve = descBreve;
    }
}
