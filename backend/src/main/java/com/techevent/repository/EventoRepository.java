package com.techevent.repository;

import com.techevent.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Consulta 1: Eventos de un tipo específico, ordenados por nombre ASC
    List<Evento> findByTipoOrderByNombreAsc(String tipo);

    // Consulta 3: Eventos con capacidad >= valor y de un ponente específico
    List<Evento> findByCapacidadGreaterThanEqualAndPonenteId(Integer capacidad, Long ponenteId);

    // Consulta 5: Todos los eventos de un ponente, ordenados por fecha DESC
    List<Evento> findByPonenteIdOrderByFechaDesc(Long ponenteId);
}
