package it.gennystabile.ricettariobe.dto.ingrediente;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class IngredienteInputDto {

    @NotBlank(message = "Il nome dell'ingrediente è obbligatorio")
    private String nome;

    @NotBlank(message = "Il nome della categoria è obbligatorio")
    private String nomeCategoria;


    private Set<@NotBlank String> stagioni;

    public Set<String> getStagioni() {
        return stagioni;
    }

    public void setStagioni(Set<String> stagioni) {
        this.stagioni = stagioni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
