package it.gennystabile.ricettariobe.dto.ricetta.composizionericetta;

import it.gennystabile.ricettariobe.dto.ingrediente.IngredienteOutputDto;

public class ComposizioneRicettaOutputDto {

    private IngredienteOutputDto ingrediente;
    private Float quantita;
    private String unitaDiMisura;

    public IngredienteOutputDto getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteOutputDto ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }

    public String getUnitaDiMisura() {
        return unitaDiMisura;
    }

    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
    }
}
