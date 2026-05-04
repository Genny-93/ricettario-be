package it.gennystabile.ricettario_be.repository;

import it.gennystabile.ricettario_be.model.CategoriaIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaIngredienteRepository extends JpaRepository<CategoriaIngrediente, Long> {
}
