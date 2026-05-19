package it.gennystabile.ricettariobe.dto.ricetta.categoria;

public class CategoriaOutputDto {

    private Long id;
    private String nomeCategoriaRicetta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoriaRicetta() {
        return nomeCategoriaRicetta;
    }

    public void setNomeCategoriaRicetta(String nomeCategoriaRicetta) {
        this.nomeCategoriaRicetta = nomeCategoriaRicetta;
    }
}
