package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.ComposizioneRicetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComposizioneRicettaRepository extends JpaRepository<ComposizioneRicetta, Long> {
}
