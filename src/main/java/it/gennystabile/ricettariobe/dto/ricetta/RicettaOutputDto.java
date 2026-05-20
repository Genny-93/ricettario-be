package it.gennystabile.ricettariobe.dto.ricetta;

import it.gennystabile.ricettariobe.dto.ricetta.categoria.CategoriaOutputDto;

import java.util.List;

public class RicettaOutputDto {

    private Long id;
    private String titolo;
    private List<CategoriaOutputDto> categorie;

    public List<CategoriaOutputDto> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<CategoriaOutputDto> categorie) {
        this.categorie = categorie;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
}
