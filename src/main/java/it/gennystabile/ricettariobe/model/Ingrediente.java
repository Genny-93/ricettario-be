package it.gennystabile.ricettariobe.model;


import it.gennystabile.ricettariobe.utils.enumeration.Colore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ingredienti")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "colore_predominante")
    private Colore colorePrincipale;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_ingrediente")
    private CategoriaIngrediente categoriaIngrediente;

    @ManyToMany
    @JoinTable(
            name = "ingredienti_stagioni",
            joinColumns = @JoinColumn(name = "id_ingrediente"),
            inverseJoinColumns = @JoinColumn(name = "id_stagione")

    )
    private List<Stagione> stagioni;

    public CategoriaIngrediente getCategoriaIngrediente() {
        return categoriaIngrediente;
    }

    public void setCategoriaIngrediente(CategoriaIngrediente categoriaIngrediente) {
        this.categoriaIngrediente = categoriaIngrediente;
    }

    public List<Stagione> getStagioni() {
        return stagioni;
    }

    public void setStagioni(List<Stagione> stagioni) {
        this.stagioni = stagioni;
    }

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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
