package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

    Optional<Ricetta> findByTitolo(String titolo);


    @Query("SELECT r.id AS id, r.titolo AS titolo, " +
            "r.tempoDiPreparazione AS tempoDiPreparazione, " +
            "r.difficolta AS difficolta, " +
            "r.votiTotali AS votiTotali, " +
            "r.valutazioneMedia AS valutazioneMedia, " +
            "r.imgPrincipale AS imgPrincipale " +
            "FROM Ricetta r")
    List<Ricetta.RicettaCardProjection> findAllRecipesForCards();

    List<Ricetta.RicettaCardProjection> findByCategorie_NomeCategoriaIgnoreCase(String nomeCategoria);

    List<Ricetta.RicettaCardProjection> findByCreatedBy_Id(Long createdBy);


}
