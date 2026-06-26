package com.techevent.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 150)
    private String nombre;

    @NotBlank
    private String tipo;

    @NotBlank
    private String fecha;

    @NotNull
    @Min(1)
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "ponente_id")
    private Ponente ponente;

    @ManyToMany(mappedBy = "eventos")
    @JsonIgnore
    private List<Patrocinador> patrocinadores;
}
