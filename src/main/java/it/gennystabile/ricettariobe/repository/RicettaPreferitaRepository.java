package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.Ricetta;
import it.gennystabile.ricettariobe.model.RicettaPreferita;
import it.gennystabile.ricettariobe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RicettaPreferitaRepository extends JpaRepository<RicettaPreferita, Long> {

    List<RicettaPreferita> findByUtente_Id(Long idUtente);

    Optional<RicettaPreferita> findByUtenteAndRicetta(User utente, Ricetta ricetta);

    boolean existsByUtente_IdAndRicetta_Id(Long idUtente, Long idRicetta);

    Long deleteByUtente_IdAndRicetta_Id(Long idUtente, Long idRicetta);


}
