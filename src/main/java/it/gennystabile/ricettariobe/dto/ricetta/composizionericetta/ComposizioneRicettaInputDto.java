package it.gennystabile.ricettariobe.dto.ricetta.composizionericetta;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ComposizioneRicettaInputDto {

    @NotBlank(message = "Il nome dell'ingrediente è obbligatorio")
    private String ingrediente;

    @NotNull(message = "La quantità è obbligatoria")
    @Positive(message = "La quantità deve essere maggiore di zero")
    private Float quantita;

    private String unitaDiMisura;

    public String getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(String ingrediente) {
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
