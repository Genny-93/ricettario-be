package it.gennystabile.ricettario_be.repository;

import it.gennystabile.ricettario_be.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

    Optional<Ricetta> findByTitolo(String titolo);

}
