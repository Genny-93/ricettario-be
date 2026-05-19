package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.CategoriaRicetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRicettaRepository extends JpaRepository<CategoriaRicetta, Long> {

    boolean existsByNomeCategoriaIgnoreCase(String nomeCategoria);
    Optional<CategoriaRicetta> findByNomeCategoria(String nomeCategoria);
}
