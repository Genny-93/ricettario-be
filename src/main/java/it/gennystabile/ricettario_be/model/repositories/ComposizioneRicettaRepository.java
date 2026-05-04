package it.gennystabile.ricettario_be.model.repositories;

import it.gennystabile.ricettario_be.model.entities.ComposizioneRicetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposizioneRicettaRepository extends JpaRepository<ComposizioneRicetta, Long> {
}
