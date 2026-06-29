package it.tcweb.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Spettacolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La data e l'ora sono obbligatorie")
    private LocalDateTime dataOra;

    @Positive(message = "Il prezzo deve essere positivo")
    private Double prezzo;

    @NotNull(message = "Il film è obbligatorio")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @NotNull(message = "La sala è obbligatoria")
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sala_id", nullable = false)
    private Sala sala;

    @OneToMany(mappedBy = "spettacolo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Prenotazione> prenotazioni;
}