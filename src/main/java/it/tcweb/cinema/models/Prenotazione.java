package it.tcweb.cinema.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il nominativo cliente è obbligatorio")
    private String nominativoCliente;

    @Min(value = 1, message = "È necessario prenotare almeno 1 posto")
    private Integer numeroPostiPrenotati;

    private LocalDateTime dataPrenotazione;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "spettacolo_id", nullable = false)
    private Spettacolo spettacolo;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}