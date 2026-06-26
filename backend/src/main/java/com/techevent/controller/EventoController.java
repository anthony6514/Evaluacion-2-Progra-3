package com.techevent.controller;

import com.techevent.model.Evento;
import com.techevent.repository.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @PostMapping
    public Evento crear(@Valid @RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtener(@PathVariable Long id) {
        return eventoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> actualizar(@PathVariable Long id, @Valid @RequestBody Evento datos) {
        return eventoRepository.findById(id).map(e -> {
            e.setNombre(datos.getNombre());
            e.setTipo(datos.getTipo());
            e.setFecha(datos.getFecha());
            e.setCapacidad(datos.getCapacidad());
            e.setPonente(datos.getPonente());
            return ResponseEntity.ok(eventoRepository.save(e));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!eventoRepository.existsById(id)) return ResponseEntity.notFound().build();
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Consulta 1: Eventos por tipo, ordenados por nombre ASC
    @GetMapping("/por-tipo")
    public List<Evento> porTipo(@RequestParam String tipo) {
        return eventoRepository.findByTipoOrderByNombreAsc(tipo);
    }

    // Consulta 3: Eventos con capacidad >= valor y de un ponente específico
    @GetMapping("/por-capacidad-ponente")
    public List<Evento> porCapacidadYPonente(@RequestParam Integer capacidad, @RequestParam Long ponenteId) {
        return eventoRepository.findByCapacidadGreaterThanEqualAndPonenteId(capacidad, ponenteId);
    }

    // Consulta 5: Eventos de un ponente, ordenados por fecha DESC
    @GetMapping("/por-ponente")
    public List<Evento> porPonente(@RequestParam Long ponenteId) {
        return eventoRepository.findByPonenteIdOrderByFechaDesc(ponenteId);
    }
}
