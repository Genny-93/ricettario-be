package it.gennystabile.ricettariobe.dto.ricetta.composizionericetta;




public class ComposizioneRicettaInputDto {


    private String ingrediente;

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
