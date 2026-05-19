package it.gennystabile.ricettariobe.dto.ingrediente;

import it.gennystabile.ricettariobe.model.CategoriaIngrediente;
import it.gennystabile.ricettariobe.utils.enumeration.Colore;

public class IngredienteInputDto {
    private String nome;
    private Colore colorePredominante;
    private CategoriaIngrediente categoriaIngrediente;


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

    public CategoriaIngrediente getCategoriaIngrediente() {
        return categoriaIngrediente;
    }

    public void setCategoriaIngrediente(CategoriaIngrediente categoriaIngrediente) {
        this.categoriaIngrediente = categoriaIngrediente;
    }
}
