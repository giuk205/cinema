package it.tcweb.cinema.service;

import it.tcweb.cinema.exceptions.RisorsaNonTrovataException;
import it.tcweb.cinema.models.Spettacolo;
import it.tcweb.cinema.repository.PrenotazioneRepository;
import it.tcweb.cinema.repository.SpettacoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpettacoloService {
    private final SpettacoloRepository spettacoloRepository;
    private final PrenotazioneRepository prenotazioneRepository;

    public List<Spettacolo> findAll() {
        return spettacoloRepository.findAll();
    }

    public Spettacolo findById(Long id) {
        return spettacoloRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Spettacolo non trovato con id: " + id));
    }

    public Spettacolo save(Spettacolo spettacolo) {
        return spettacoloRepository.save(spettacolo);
    }

    public Spettacolo update(Long id, Spettacolo spettacolo) {
        Spettacolo existing = findById(id);
        existing.setDataOra(spettacolo.getDataOra());
        existing.setPrezzo(spettacolo.getPrezzo());
        existing.setFilm(spettacolo.getFilm());
        existing.setSala(spettacolo.getSala());
        return spettacoloRepository.save(existing);
    }

    public void delete(Long id) {
        if (!spettacoloRepository.existsById(id)) throw new RisorsaNonTrovataException("Spettacolo non trovato");
        spettacoloRepository.deleteById(id);
    }

    // Metodi Bonus
    public List<Spettacolo> getSpettacoliFuturi() {
        return spettacoloRepository.findByDataOraAfter(LocalDateTime.now());
    }

    public int getDisponibilita(Long id) {
        Spettacolo s = findById(id);
        Integer occupati = prenotazioneRepository.sumPostiPrenotatiBySpettacoloId(id);
        if (occupati == null) occupati = 0;
        return s.getSala().getNumeroPosti() - occupati;
    }
}