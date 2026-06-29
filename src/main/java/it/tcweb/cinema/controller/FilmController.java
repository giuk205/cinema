package it.tcweb.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tcweb.cinema.models.Film;
import it.tcweb.cinema.service.FilmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/film")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Film", description = "API per la gestione e ricerca dei film")
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    @Operation(summary = "Elenco film", description = "Restituisce la lista di tutti i film, opzionalmente filtrata per titolo o genere")
    @ApiResponse(responseCode = "200", description = "Operazione riuscita")
    public ResponseEntity<List<Film>> getAll(
            @Parameter(description = "Filtro per titolo (opzionale)") @RequestParam(required = false) String titolo,
            @Parameter(description = "Filtro per genere (opzionale)") @RequestParam(required = false) String genere) {
        return ResponseEntity.ok(filmService.ricerca(titolo, genere));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trova film per ID", description = "Restituisce i dettagli di un singolo film tramite il suo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film trovato"),
            @ApiResponse(responseCode = "404", description = "Film non trovato")
    })
    public ResponseEntity<Film> getById(@Parameter(description = "ID del film da cercare") @PathVariable Long id) {
        return ResponseEntity.ok(filmService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea nuovo film", description = "Aggiunge un nuovo film al database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Film creato con successo"),
            @ApiResponse(responseCode = "400", description = "Dati inseriti non validi")
    })
    public ResponseEntity<Film> create(@Valid @RequestBody Film film) {
        return ResponseEntity.status(201).body(filmService.save(film));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna film", description = "Modifica i dati di un film esistente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Film aggiornato"),
            @ApiResponse(responseCode = "404", description = "Film non trovato"),
            @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<Film> update(@Parameter(description = "ID del film da modificare") @PathVariable Long id, @Valid @RequestBody Film film) {
        return ResponseEntity.ok(filmService.update(id, film));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina film", description = "Rimuove definitivamente un film dal database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Film eliminato con successo"),
            @ApiResponse(responseCode = "404", description = "Film non trovato")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID del film da eliminare") @PathVariable Long id) {
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}