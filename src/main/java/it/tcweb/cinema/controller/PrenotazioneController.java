package it.tcweb.cinema.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.tcweb.cinema.models.Prenotazione;
import it.tcweb.cinema.service.PrenotazioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@Tag(name = "Prenotazioni", description = "API per la gestione e creazione delle prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @GetMapping
    @Operation(summary = "Elenco prenotazioni", description = "Restituisce la lista completa di tutte le prenotazioni effettuate")
    @ApiResponse(responseCode = "200", description = "Operazione riuscita")
    public ResponseEntity<List<Prenotazione>> getAll() {
        return ResponseEntity.ok(prenotazioneService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Trova prenotazione per ID", description = "Restituisce i dettagli di una specifica prenotazione tramite ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prenotazione trovata"),
            @ApiResponse(responseCode = "404", description = "Prenotazione non trovata")
    })
    public ResponseEntity<Prenotazione> getById(@Parameter(description = "ID della prenotazione") @PathVariable Long id) {
        return ResponseEntity.ok(prenotazioneService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crea nuova prenotazione", description = "Effettua una prenotazione. Verifica automaticamente la disponibilità dei posti per lo spettacolo selezionato")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prenotazione creata con successo"),
            @ApiResponse(responseCode = "400", description = "Dati non validi o posti insufficienti")
    })
    public ResponseEntity<Prenotazione> create(@Valid @RequestBody Prenotazione p) {
        return ResponseEntity.status(201).body(prenotazioneService.creaPrenotazione(p));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Aggiorna prenotazione", description = "Modifica i dati di una prenotazione esistente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prenotazione aggiornata"),
            @ApiResponse(responseCode = "404", description = "Prenotazione non trovata"),
            @ApiResponse(responseCode = "400", description = "Dati non validi")
    })
    public ResponseEntity<Prenotazione> update(@Parameter(description = "ID della prenotazione da modificare") @PathVariable Long id, @Valid @RequestBody Prenotazione p) {
        return ResponseEntity.ok(prenotazioneService.update(id, p));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina prenotazione", description = "Cancella una prenotazione esistente e libera i posti")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prenotazione eliminata con successo"),
            @ApiResponse(responseCode = "404", description = "Prenotazione non trovata")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "ID della prenotazione da eliminare") @PathVariable Long id) {
        prenotazioneService.cancellaPrenotazione(id);
        return ResponseEntity.noContent().build();
    }
}