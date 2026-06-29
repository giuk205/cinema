package it.tcweb.cinema.service;

import it.tcweb.cinema.exceptions.RisorsaNonTrovataException;
import it.tcweb.cinema.models.Sala;
import it.tcweb.cinema.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalaService {
    private final SalaRepository salaRepository;

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Sala non trovata con id: " + id));
    }

    public Sala save(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala update(Long id, Sala sala) {
        Sala existing = findById(id);
        existing.setNome(sala.getNome());
        existing.setNumeroPosti(sala.getNumeroPosti());
        return salaRepository.save(existing);
    }

    public void delete(Long id) {
        if (!salaRepository.existsById(id)) throw new RisorsaNonTrovataException("Sala non trovata");
        salaRepository.deleteById(id);
    }
}

