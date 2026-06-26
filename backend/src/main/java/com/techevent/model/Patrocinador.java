package com.techevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "patrocinadores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patrocinador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String empresa;

    @NotBlank
    private String sector;

    @NotNull
    @DecimalMin(value = "0.01")
    private Double montoAporte;

    @ManyToMany
    @JoinTable(
        name = "patrocinador_evento",
        joinColumns = @JoinColumn(name = "patrocinador_id"),
        inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    private List<Evento> eventos;
}
