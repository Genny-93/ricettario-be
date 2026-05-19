package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    Optional<Ingrediente> findByNome(String nome);
    boolean existsByNomeIgnoreCase(String nome);

}
