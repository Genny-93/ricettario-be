package it.gennystabile.ricettariobe.model;


import it.gennystabile.ricettariobe.utils.enumeration.TipoFile;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

@Entity
@Table(name = "multimedia")
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "tipo_multimedia", columnDefinition = "tipo_file")
    private TipoFile tipoFile;

    @Column(name = "url")
    private String url;

    @Column(name = "formato")
    private String formato;

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

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
