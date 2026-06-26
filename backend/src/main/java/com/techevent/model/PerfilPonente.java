package com.techevent.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "perfiles_ponente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilPonente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 500)
    private String biografia;

    @Size(max = 255)
    private String linkedin;

    @Size(max = 255)
    private String paginaWeb;

    @OneToOne
    @JoinColumn(name = "ponente_id")
    private Ponente ponente;
}
