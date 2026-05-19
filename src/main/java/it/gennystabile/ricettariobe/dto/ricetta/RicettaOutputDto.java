package it.gennystabile.ricettariobe.dto.ricetta;

public class RicettaOutputDto {

    private Long id;
    private String titolo;

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
