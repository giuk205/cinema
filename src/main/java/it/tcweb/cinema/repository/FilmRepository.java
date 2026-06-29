package it.tcweb.cinema.repository;

import it.tcweb.cinema.models.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {
    // Ricerca per titolo (anche parziale e case-insensitive)
    List<Film> findByTitoloContainingIgnoreCase(String titolo);

    // Ricerca per genere
    List<Film> findByGenere(String genere);
}