package com.techevent.controller;

import com.techevent.model.Evento;
import com.techevent.repository.EventoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    public Evento crear(@Valid @RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Evento obtener(@PathVariable Long id) {
        Optional<Evento> resultado = eventoRepository.findById(id);
        if (resultado.isPresent()) {
            return resultado.get();
        }
        return null;
    }

    @PutMapping("/{id}")
    public Evento actualizar(@PathVariable Long id, @Valid @RequestBody Evento datos) {
        Optional<Evento> resultado = eventoRepository.findById(id);
        if (resultado.isPresent()) {
            Evento evento = resultado.get();
            evento.setNombre(datos.getNombre());
            evento.setTipo(datos.getTipo());
            evento.setFecha(datos.getFecha());
            evento.setCapacidad(datos.getCapacidad());
            evento.setPonente(datos.getPonente());
            return eventoRepository.save(evento);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (eventoRepository.existsById(id)) {
            eventoRepository.deleteById(id);
            return "Evento eliminado";
        }
        return "No encontrado";
    }

    // Consulta 1: eventos por tipo ordenados por nombre
    @GetMapping("/por-tipo")
    public List<Evento> porTipo(@RequestParam String tipo) {
        return eventoRepository.findByTipoOrderByNombreAsc(tipo);
    }

    // Consulta 3: eventos con capacidad minima de un ponente
    @GetMapping("/por-capacidad-ponente")
    public List<Evento> porCapacidadYPonente(@RequestParam Integer capacidad, @RequestParam Long ponenteId) {
        return eventoRepository.findByCapacidadGreaterThanEqualAndPonenteId(capacidad, ponenteId);
    }

    // Consulta 5: eventos de un ponente ordenados por fecha
    @GetMapping("/por-ponente")
    public List<Evento> porPonente(@RequestParam Long ponenteId) {
        return eventoRepository.findByPonenteIdOrderByFechaDesc(ponenteId);
    }
}
