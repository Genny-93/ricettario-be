package it.gennystabile.ricettariobe.dto.ingrediente;

import it.gennystabile.ricettariobe.dto.ingrediente.stagione.StagioneOutputDto;
import it.gennystabile.ricettariobe.utils.enumeration.Colore;


import java.util.Set;

public class IngredienteOutputDto {

    private String nome;
    private Colore colorePrincipale;
    private Set<StagioneOutputDto> stagioni;

    public Set<StagioneOutputDto> getStagioni() {
        return stagioni;
    }

    public void setStagioni(Set<StagioneOutputDto> stagioni) {
        this.stagioni = stagioni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Colore getColorePrincipale() {
        return colorePrincipale;
    }

    public void setColorePrincipale(Colore colorePrincipale) {
        this.colorePrincipale = colorePrincipale;
    }
}
