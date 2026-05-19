package it.gennystabile.ricettariobe.repository;

import it.gennystabile.ricettariobe.model.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultimediaRepository extends JpaRepository<Multimedia, Long> {
}
