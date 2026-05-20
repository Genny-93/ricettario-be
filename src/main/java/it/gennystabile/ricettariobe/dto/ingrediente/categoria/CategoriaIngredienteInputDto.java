package it.gennystabile.ricettariobe.dto.ingrediente.categoria;

import jakarta.validation.constraints.NotBlank;

public class CategoriaIngredienteInputDto {

    @NotBlank
    private String nomeCategoria;

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
