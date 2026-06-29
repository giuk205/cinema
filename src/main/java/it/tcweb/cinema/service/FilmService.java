package it.tcweb.cinema.service;

import it.tcweb.cinema.exceptions.RisorsaNonTrovataException;
import it.tcweb.cinema.models.Film;
import it.tcweb.cinema.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public List<Film> ricerca(String titolo, String genere) {
        if (titolo == null && genere == null) return filmRepository.findAll();
        // Se hai dei metodi personalizzati nel repository per filtrare, usali qui
        return filmRepository.findAll();
    }

    public Film findById(Long id) {
        return filmRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Film non trovato con id: " + id));
    }

    public Film save(Film film) {
        return filmRepository.save(film);
    }

    public Film update(Long id, Film film) {
        Film existing = findById(id);
        existing.setTitolo(film.getTitolo());
        existing.setGenere(film.getGenere());
        existing.setDurataMinuti(film.getDurataMinuti());
        existing.setDataUscita(film.getDataUscita());
        return filmRepository.save(existing);
    }

    public void delete(Long id) {
        if (!filmRepository.existsById(id)) throw new RisorsaNonTrovataException("Film non trovato");
        filmRepository.deleteById(id);
    }
}