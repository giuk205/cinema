package it.tcweb.cinema.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Gestione 404
    @ExceptionHandler(RisorsaNonTrovataException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(RisorsaNonTrovataException ex){
        ErrorResponse body = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(404).body(body);
    }

    // Gestione 400 (Logica di business)
    @ExceptionHandler(PrenotazioneNonConsentitaException.class)
    public ResponseEntity<ErrorResponse> handleBusinessError(PrenotazioneNonConsentitaException ex){
        ErrorResponse body = new ErrorResponse(400, ex.getMessage());
        return ResponseEntity.status(400).body(body);
    }

    // 3. Gestisce gli errori di validazione (i campi obbligatori)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex){
        Map<String, String> errori = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errori.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errori);
    }

    // 4. Gestisce tutti gli altri errori imprevisti
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex){
        ErrorResponse body = new ErrorResponse(500, ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }
}

