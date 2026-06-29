package it.tcweb.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tcweb.cinema.models.Cliente;
import it.tcweb.cinema.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/clienti")
@RequiredArgsConstructor
@Tag(name = "Clienti", description = "API per la gestione dell'anagrafica clienti")
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Elenco clienti", description = "Restituisce la lista di tutti i clienti registrati")
    @ApiResponse(responseCode = "200", description = "Operazione riuscita")
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trova cliente per ID", description = "Restituisce i dettagli di un cliente tramite ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente trovato"),
            @ApiResponse(responseCode = "404", description = "Cliente non trovato")
    })
    public ResponseEntity<Cliente> getById(@Parameter(description = "ID del cliente") @PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea nuovo cliente", description = "Registra un nuovo cliente nel database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creato con successo"),
            @ApiResponse(responseCode = "400", description = "Dati inseriti non validi")
    })
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        return ResponseEntity.status(201).body(clienteService.save(cliente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna cliente", description = "Modifica le informazioni di un cliente esistente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente aggiornato"),
            @ApiResponse(responseCode = "404", description = "Cliente non trovato"),
            @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<Cliente> update(@Parameter(description = "ID del cliente da modificare") @PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.update(id, cliente));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina cliente", description = "Rimuove un cliente dal database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminato con successo"),
            @ApiResponse(responseCode = "404", description = "Cliente non trovato")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID del cliente da eliminare") @PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}