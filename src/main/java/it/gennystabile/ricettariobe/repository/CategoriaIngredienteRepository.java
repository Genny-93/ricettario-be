package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.CategoriaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaIngredienteRepository extends JpaRepository<CategoriaIngrediente, Long> {

    boolean existsByNomeCategoriaIgnoreCase(String nome);

    Optional<CategoriaIngrediente> findByNomeCategoria(String nome);
}
