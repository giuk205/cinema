package it.tcweb.cinema.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Il titolo è obbligatorio")
    private String titolo;

    private String descrizione;

    @Positive(message = "La durata deve essere un numero positivo")
    private Integer durataMinuti;

    private String genere;
    private LocalDate dataUscita;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Spettacolo> spettacoli;
}
