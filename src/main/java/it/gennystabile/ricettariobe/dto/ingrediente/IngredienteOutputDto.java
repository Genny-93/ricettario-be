package it.gennystabile.ricettariobe.dto.ingrediente;

import it.gennystabile.ricettariobe.utils.enumeration.Colore;

public class IngredienteOutputDto {

    private String nome;
    private Colore colorePredominante;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Colore getColorePredominante() {
        return colorePredominante;
    }

    public void setColorePredominante(Colore colorePredominante) {
        this.colorePredominante = colorePredominante;
    }
}
