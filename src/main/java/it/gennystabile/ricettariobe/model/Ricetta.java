package it.gennystabile.ricettariobe.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ricette")
public class Ricetta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titolo", nullable = false)
    private String titolo;

    @Column(name = "tempo_di_preparazione")
    private Short tempoDiPreparazione;

    @Column(name = "difficolta")
    private Float difficolta;

    @Column(name = "procedimento", columnDefinition = "TEXT")
    private String procedimento;

    @Column(name = "desc_breve")
    private String descBreve;

    @Column(name = "voti_totali")
    private Integer votiTotali;

    @Column(name = "valutazione_media")
    private Float valutazioneMedia;

    @Column(name = "url_immagine_principale")
    private String imgPrincipale;

    @ManyToMany
    @JoinTable(
            name = "ricette_categorie",
            joinColumns = @JoinColumn(name = "ricetta_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<CategoriaRicetta> categorie;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComposizioneRicetta> composizioneRicetta;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @OneToMany(mappedBy = "ricetta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Multimedia> multimedia;

    public List<ComposizioneRicetta> getComposizioneRicetta() {
        return composizioneRicetta;
    }

    public String getImgPrincipale() {
        return imgPrincipale;
    }

    public void setImgPrincipale(String imgPrincipale) {
        this.imgPrincipale = imgPrincipale;
    }

    public void setComposizioneRicetta(List<ComposizioneRicetta> composizioneRicetta) {
        this.composizioneRicetta = composizioneRicetta;
    }

    public Integer getVotiTotali() {
        return votiTotali;
    }

    public void setVotiTotali(Integer votiTotali) {
        this.votiTotali = votiTotali;
    }

    public Float getValutazioneMedia() {
        return valutazioneMedia;
    }

    public void setValutazioneMedia(Float valutazioneMedia) {
        this.valutazioneMedia = valutazioneMedia;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public List<CategoriaRicetta> getCategorie() {
        return categorie;
    }

    public void setCategorie(List<CategoriaRicetta> categoria) {
        this.categorie = categoria;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescBreve() {
        return descBreve;
    }

    public void setDescBreve(String descBreve) {
        this.descBreve = descBreve;
    }

    public String getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(String procedimento) {
        this.procedimento = procedimento;
    }

    public Float getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(Float difficolta) {
        this.difficolta = difficolta;
    }

    public Short getTempoDiPreparazione() {
        return tempoDiPreparazione;
    }

    public void setTempoDiPreparazione(Short tempoDiPreparazione) {
        this.tempoDiPreparazione = tempoDiPreparazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public interface RicettaCardProjection {
        Long getId();
        String getTitolo();
        Short getTempoDiPreparazione();
        Float getDifficolta();
        Integer getVotiTotali();
        Float getValutazioneMedia();
        String getImgPrincipale();
        String getDescBreve();
    }
}
