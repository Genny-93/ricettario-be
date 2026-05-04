package it.gennystabile.ricettario_be.repository;

import it.gennystabile.ricettario_be.model.Stagione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StagioneRepository extends JpaRepository<Stagione, Long> {
}
