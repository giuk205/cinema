package it.tcweb.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tcweb.cinema.models.Spettacolo;
import it.tcweb.cinema.service.SpettacoloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spettacoli")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Spettacoli", description = "API per la gestione della programmazione degli spettacoli")
public class SpettacoloController {

    private final SpettacoloService spettacoloService;

    @GetMapping
    @Operation(summary = "Elenco spettacoli", description = "Restituisce la lista completa di tutti gli spettacoli in programmazione")
    @ApiResponse(responseCode = "200", description = "Operazione riuscita")
    public ResponseEntity<List<Spettacolo>> getAll() {
        return ResponseEntity.ok(spettacoloService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trova spettacolo per ID", description = "Restituisce i dettagli di uno specifico spettacolo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spettacolo trovato"),
            @ApiResponse(responseCode = "404", description = "Spettacolo non trovato")
    })
    public ResponseEntity<Spettacolo> getById(@Parameter(description = "ID dello spettacolo") @PathVariable Long id) {
        return ResponseEntity.ok(spettacoloService.findById(id));
    }

    @GetMapping("/futuri")
    @Operation(summary = "Spettacoli futuri", description = "Restituisce la lista degli spettacoli in programma da oggi in poi")
    @ApiResponse(responseCode = "200", description = "Operazione riuscita")
    public ResponseEntity<List<Spettacolo>> getFuturi() {
        return ResponseEntity.ok(spettacoloService.getSpettacoliFuturi());
    }

    @GetMapping("/{id}/disponibilita")
    @Operation(summary = "Disponibilità posti", description = "Calcola il numero di posti ancora disponibili per uno specifico spettacolo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operazione riuscita"),
            @ApiResponse(responseCode = "404", description = "Spettacolo non trovato")
    })
    public ResponseEntity<Integer> getDisponibilita(@Parameter(description = "ID dello spettacolo") @PathVariable Long id) {
        return ResponseEntity.ok(spettacoloService.getDisponibilita(id));
    }

    @PostMapping
    @Operation(summary = "Crea spettacolo", description = "Inserisce un nuovo spettacolo nel database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Spettacolo creato con successo"),
            @ApiResponse(responseCode = "400", description = "Dati inseriti non validi")
    })
    public ResponseEntity<Spettacolo> create(@Valid @RequestBody Spettacolo spettacolo) {
        return ResponseEntity.status(201).body(spettacoloService.save(spettacolo));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna spettacolo", description = "Modifica le informazioni di uno spettacolo esistente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spettacolo aggiornato"),
            @ApiResponse(responseCode = "404", description = "Spettacolo non trovato"),
            @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<Spettacolo> update(@Parameter(description = "ID dello spettacolo da modificare") @PathVariable Long id, @Valid @RequestBody Spettacolo spettacolo) {
        return ResponseEntity.ok(spettacoloService.update(id, spettacolo));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina spettacolo", description = "Rimuove definitivamente uno spettacolo dal database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Spettacolo eliminato con successo"),
            @ApiResponse(responseCode = "404", description = "Spettacolo non trovato")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID dello spettacolo da eliminare") @PathVariable Long id) {
        spettacoloService.delete(id);
        return ResponseEntity.noContent().build();
    }
}