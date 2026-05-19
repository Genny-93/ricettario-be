package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.Stagione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagioneRepository extends JpaRepository<Stagione, Long> {
}
