package it.gennystabile.ricettario_be.dto.ricetta;

public class RicettaInputDto {

    private String titolo;
    private Short tempoDiPreparazione;
    private Float difficolta;
    private String procedimento;
    private String descBreve;

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
