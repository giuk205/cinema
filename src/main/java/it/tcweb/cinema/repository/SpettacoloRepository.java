package it.tcweb.cinema.repository;

import it.tcweb.cinema.models.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long> {

    // Spring leggerà "findByDataOraAfter" e creerà automaticamente la query SQL!
    List<Spettacolo> findByDataOraAfter(LocalDateTime data);

}