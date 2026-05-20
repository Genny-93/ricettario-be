package it.gennystabile.ricettariobe.dto.ingrediente;

import java.util.Set;

public class IngredienteInputDto {
    private String nome;
    private String nomeCategoria;
    private Set<String> stagioni;

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
