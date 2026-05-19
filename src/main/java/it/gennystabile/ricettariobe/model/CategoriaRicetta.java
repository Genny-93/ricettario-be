package it.gennystabile.ricettariobe.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "categorie_ricetta")
public class CategoriaRicetta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cat", length = 100)
    private String nomeCategoria;

    @ManyToMany(mappedBy = "categorie")
    private List<Ricetta> ricette;

    public List<Ricetta> getRicette() {
        return ricette;
    }

    public void setRicette(List<Ricetta> ricette) {
        this.ricette = ricette;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
