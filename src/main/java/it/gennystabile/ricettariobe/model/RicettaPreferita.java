package it.gennystabile.ricettariobe.model;


import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;


import java.time.LocalDateTime;

@Entity
@Table(name = "ricette_preferite")
public class RicettaPreferita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ricetta", nullable = false)
    private Ricetta ricetta;

    @ManyToOne
    @JoinColumn(name = "id_utente", nullable = false)
    private User utente;

    @Column(name = "liked_at")
    @Generated(event = EventType.INSERT) // Dice a Hibernate: "Non inviare questo campo nella INSERT, lo genera il DB"
    private LocalDateTime likedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public User getUtente() {
        return utente;
    }

    public void setUtente(User utente) {
        this.utente = utente;
    }
}
