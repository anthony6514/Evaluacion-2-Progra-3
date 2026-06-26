package com.techevent.repository;

import com.techevent.model.Patrocinador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatrocinadorRepository extends JpaRepository<Patrocinador, Long> {

    // Consulta 4: Patrocinadores con monto entre min y max, ordenados por monto DESC
    List<Patrocinador> findByMontoAporteBetweenOrderByMontoAporteDesc(Double min, Double max);
}
