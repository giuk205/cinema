package it.tcweb.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tcweb.cinema.models.Sala;
import it.tcweb.cinema.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Sale", description = "API per la gestione delle sale cinematografiche")
public class SalaController {

    private final SalaService salaService;

    @GetMapping
    @Operation(summary = "Elenco sale", description = "Restituisce la lista completa di tutte le sale disponibili")
    @ApiResponse(responseCode = "200", description = "Operazione riuscita")
    public ResponseEntity<List<Sala>> getAll() {
        return ResponseEntity.ok(salaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trova sala per ID", description = "Restituisce i dettagli di una specifica sala tramite ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala trovata"),
            @ApiResponse(responseCode = "404", description = "Sala non trovata")
    })
    public ResponseEntity<Sala> getById(@Parameter(description = "ID della sala") @PathVariable Long id) {
        return ResponseEntity.ok(salaService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea nuova sala", description = "Aggiunge una nuova sala al sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sala creata con successo"),
            @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<Sala> create(@Valid @RequestBody Sala sala) {
        return ResponseEntity.status(201).body(salaService.save(sala));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna sala", description = "Modifica i dati di una sala esistente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sala aggiornata"),
            @ApiResponse(responseCode = "404", description = "Sala non trovata"),
            @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<Sala> update(@Parameter(description = "ID della sala da modificare") @PathVariable Long id, @Valid @RequestBody Sala sala) {
        return ResponseEntity.ok(salaService.update(id, sala));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina sala", description = "Rimuove una sala dal database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sala eliminata con successo"),
            @ApiResponse(responseCode = "404", description = "Sala non trovata")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID della sala da eliminare") @PathVariable Long id) {
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}