package it.gennystabile.ricettariobe.dto.ingrediente;

import it.gennystabile.ricettariobe.utils.enumeration.Colore;



public class IngredienteOutputDto {

    private Long id;
    private String nome;
    private Colore colorePrincipale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
