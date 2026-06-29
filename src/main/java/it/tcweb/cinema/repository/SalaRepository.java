package it.tcweb.cinema.repository;
import it.tcweb.cinema.models.Sala;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {
    // Metodi CRUD standard bastano per ora
}