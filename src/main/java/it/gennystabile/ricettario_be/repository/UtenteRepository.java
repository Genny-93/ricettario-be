package it.gennystabile.ricettario_be.repository;

import it.gennystabile.ricettario_be.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
}
