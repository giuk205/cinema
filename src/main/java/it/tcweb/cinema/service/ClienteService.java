package it.tcweb.cinema.service;

import it.tcweb.cinema.exceptions.RisorsaNonTrovataException;
import it.tcweb.cinema.models.Cliente;
import it.tcweb.cinema.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RisorsaNonTrovataException("Cliente non trovato con id: " + id));
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente cliente) {
        Cliente existing = findById(id);
        existing.setNome(cliente.getNome());
        existing.setCognome(cliente.getCognome());
        existing.setEmail(cliente.getEmail());
        return clienteRepository.save(existing);
    }

    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RisorsaNonTrovataException("Cliente non trovato, impossibile eliminare");
        }
        clienteRepository.deleteById(id);
    }
}