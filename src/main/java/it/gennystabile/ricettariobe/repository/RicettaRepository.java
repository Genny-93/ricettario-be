package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

    Optional<Ricetta> findByTitolo(String titolo);

}
