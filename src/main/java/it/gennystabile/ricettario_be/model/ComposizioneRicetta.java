package it.gennystabile.ricettario_be.model;

import jakarta.persistence.*;

@Entity
@Table(name = "composizioni_ricetta")
public class ComposizioneRicetta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ricetta")
    private Ricetta ricetta;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente ingrediente;

    @Column(name = "quantita")
    private Float quantita;

    @Column(name = "unita_di_misura", length = 20)
    private String unitaDiMisura;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }

    public String getUnitaDiMisura() {
        return unitaDiMisura;
    }

    public void setUnitaDiMisura(String unitaDiMisura) {
        this.unitaDiMisura = unitaDiMisura;
    }
}
