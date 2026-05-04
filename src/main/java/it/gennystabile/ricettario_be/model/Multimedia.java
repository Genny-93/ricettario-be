package it.gennystabile.ricettario_be.model;


import it.gennystabile.ricettario_be.utils.enumeration.TipoFile;
import jakarta.persistence.*;

@Entity
@Table(name = "multimedia")
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_multimedia", columnDefinition = "tipo_file")
    private TipoFile tipoFile;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_ricetta")
    private Ricetta ricetta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoFile getTipoFile() {
        return tipoFile;
    }

    public void setTipoFile(TipoFile tipoFile) {
        this.tipoFile = tipoFile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Ricetta getRicetta() {
        return ricetta;
    }

    public void setRicetta(Ricetta idRicetta) {
        this.ricetta = idRicetta;
    }
}
