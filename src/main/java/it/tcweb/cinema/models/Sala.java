package it.tcweb.cinema.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Min(value = 1, message = "Il numero di posti deve essere almeno 1")
    private Integer numeroPosti;

    @OneToMany(mappedBy = "sala")
    private List<Spettacolo> spettacoli;
}