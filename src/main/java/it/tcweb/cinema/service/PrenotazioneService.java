package it.tcweb.cinema.service;

import it.tcweb.cinema.exceptions.PrenotazioneNonConsentitaException;
import it.tcweb.cinema.exceptions.RisorsaNonTrovataException;
import it.tcweb.cinema.models.Prenotazione;
import it.tcweb.cinema.repository.ClienteRepository;
import it.tcweb.cinema.repository.PrenotazioneRepository;
import it.tcweb.cinema.repository.SpettacoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final SpettacoloRepository spettacoloRepository;
    private final ClienteRepository clienteRepository; // Aggiunto

    public List<Prenotazione> findAll() {
        return prenotazioneRepository.findAll();
    }

    public Prenotazione findById(Long id) {
        return prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Prenotazione non trovata con id: " + id));
    }

    public Prenotazione creaPrenotazione(Prenotazione p) {
        // 1. Recupera il cliente dal database
        var cliente = clienteRepository.findById(p.getCliente().getId())
                .orElseThrow(() -> new RisorsaNonTrovataException("Cliente non trovato con id: " + p.getCliente().getId()));

        // 2. Recupera lo spettacolo dal database
        var spettacolo = spettacoloRepository.findById(p.getSpettacolo().getId())
                .orElseThrow(() -> new RisorsaNonTrovataException("Spettacolo non trovato"));

        // 3. Logica verifica posti
        Integer occupati = prenotazioneRepository.sumPostiPrenotatiBySpettacoloId(spettacolo.getId());
        if (occupati == null) occupati = 0;
        int disponibilita = spettacolo.getSala().getNumeroPosti() - occupati;

        if (p.getNumeroPostiPrenotati() > disponibilita) {
            throw new PrenotazioneNonConsentitaException("Posti insufficienti!");
        }

        // 4. Assegna gli oggetti completi
        p.setCliente(cliente);
        p.setSpettacolo(spettacolo);
        p.setDataPrenotazione(LocalDateTime.now());

        return prenotazioneRepository.save(p);
    }

    public Prenotazione update(Long id, Prenotazione p) {
        Prenotazione existing = findById(id);

        // Aggiorna il cliente
        var cliente = clienteRepository.findById(p.getCliente().getId())
                .orElseThrow(() -> new RisorsaNonTrovataException("Cliente non trovato"));

        existing.setCliente(cliente);
        existing.setNumeroPostiPrenotati(p.getNumeroPostiPrenotati());

        return prenotazioneRepository.save(existing);
    }

    public void cancellaPrenotazione(Long id) {
        if (!prenotazioneRepository.existsById(id)) throw new RisorsaNonTrovataException("Non trovata");
        prenotazioneRepository.deleteById(id);
    }
}