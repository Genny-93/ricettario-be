package it.gennystabile.ricettario_be.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorie_ingrediente")
public class CategoriaIngrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_cat", length = 100)
    private String nomeCategoria;

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
