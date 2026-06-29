package it.tcweb.cinema.repository;

import it.tcweb.cinema.models.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    // Query per sommare i posti prenotati di uno specifico spettacolo
    @Query("SELECT SUM(p.numeroPostiPrenotati) FROM Prenotazione p WHERE p.spettacolo.id = :spettacoloId")
    Integer sumPostiPrenotatiBySpettacoloId(@Param("spettacoloId") Long spettacoloId);
}