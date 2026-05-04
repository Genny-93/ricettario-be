package it.gennystabile.ricettario_be.repository;

import it.gennystabile.ricettario_be.model.CategoriaRicetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRicettaRepository extends JpaRepository<CategoriaRicetta, Long> {
}
