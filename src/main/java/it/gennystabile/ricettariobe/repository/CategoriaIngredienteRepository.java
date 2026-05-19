package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.CategoriaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaIngredienteRepository extends JpaRepository<CategoriaIngrediente, Long> {
}
