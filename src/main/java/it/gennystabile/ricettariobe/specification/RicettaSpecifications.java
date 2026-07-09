package it.gennystabile.ricettariobe.specification;

import it.gennystabile.ricettariobe.model.CategoriaRicetta;
import it.gennystabile.ricettariobe.model.Ricetta;
import it.gennystabile.ricettariobe.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.data.jpa.domain.Specification;

public class RicettaSpecifications {


    public static Specification<Ricetta> hasUsername(String username) {
        //root rappresenta la tabella Ricetta
        //query la query in costruzione
        //cb sarebbe il criteriaBuilder, che serve per costruire condizioni SQL
        return ((root, query, cb) -> {
            if (username == null || username.isBlank()) {
                return null;
            }

            //Join tra Ricetta e User con createdBy presente in Ricetta
            Join<Ricetta, User> user = root.join("createdBy");
            return cb.like(//ritorna una condizione SQL di tipo LIKE
                    //prende colonna username di user in minuscolo
                    cb.lower(user.get("username")),
                    "%" + username.toLowerCase() + "%"
            );
        });
    }

    //SELECT DISTINCT r.*
    //FROM ricette r
    //JOIN ricette_categorie rc ON rc.ricetta_id = r.id
    //JOIN categorie c ON c.id = rc.categoria_id
    //WHERE c.nomeCategoria = category
    public static Specification<Ricetta> hasCategory(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null || category.isBlank()) {
                return null;
            }

            //Join tra Ricetta e CategoriaRicetta con categorie presente in Ricetta
            // Hibernate genera una JOIN sulla tabella ponte
            // ricette_categorie e poi sulla tabella categorie.
            Join<Ricetta, CategoriaRicetta> cat = root.join("categorie");
            // evita duplicati
            query.distinct(true);
            //Ritorna una condizione di uguaglianza (=)
            return criteriaBuilder.equal(cat.get("nomeCategoria"), category);
        };
    }

    //Filtra le ricette con tempo di preparazione <= al valore input
    //WHERE tempo_di_preparazione <= tempo
    public static Specification<Ricetta> maxTempo(Float tempo) {
        return (root, query, criteriaBuilder) -> {
            if (tempo == null) {
                return null;
            }

            return criteriaBuilder.lessThanOrEqualTo(
                    root.get("tempoDiPreparazione"), tempo.shortValue()
                    //si converte tempo in short perché nell'entità il campo è Short
            );
        };
    }

    //WHERE tempo_di_preparazione >= tempo
    public static Specification<Ricetta> minTempo(Float tempo) {
        return ((root, query, criteriaBuilder) -> {
            if (tempo == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(
                    root.get("tempoDiPreparazione"),
                    tempo.shortValue()
            );
        });
    }

    //WHERE difficolta between (min, max)
    public static Specification<Ricetta> hasDifficolta(String difficolta) {
        return (root, query, criteriaBuilder) -> {
            if (difficolta == null || difficolta.isBlank()) {
                return null;
            }

            switch (difficolta.toUpperCase()) {
                case "FACILE":
                    return criteriaBuilder.between(root.get("difficolta"), 0.1, 1.50);
                case "MEDIO":
                    return criteriaBuilder.between(root.get("difficolta"), 1.51, 3.00);
                case "DIFFICILE":
                    return criteriaBuilder.between(root.get("difficolta"), 3.01, 4.50);
                case "ESPERTO":
                    return criteriaBuilder.between(root.get("difficolta"), 4.51, 5.00);
                default:
                    throw new IllegalArgumentException("Fascia difficoltà non valida: " + difficolta);
            }
        };
    }

    //WHERE valutazione_media >= valutazione
    public static Specification<Ricetta> minValutazione(Float valutazione) {
        return (root, query, criteriaBuilder) -> {
            if (valutazione == null) {
                return null;
            }

            return criteriaBuilder.greaterThanOrEqualTo(root.get("valutazioneMedia"),
                    valutazione);
        };

    }
}


/* @RequestParam(required = false) String username,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Float tempoDiCottura,
            @RequestParam(required = false) Float difficolta,
            @RequestParam(required = false) Float valutazioneMedia
*/
