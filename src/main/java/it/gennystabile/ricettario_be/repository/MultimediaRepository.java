package it.gennystabile.ricettario_be.repository;

import it.gennystabile.ricettario_be.model.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
}
