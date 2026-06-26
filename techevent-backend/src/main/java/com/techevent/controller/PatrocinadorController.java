package com.techevent.controller;

import com.techevent.model.Patrocinador;
import com.techevent.repository.PatrocinadorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrocinadores")
@CrossOrigin(origins = "*")
public class PatrocinadorController {

    private final PatrocinadorRepository patrocinadorRepository;

    public PatrocinadorController(PatrocinadorRepository patrocinadorRepository) {
        this.patrocinadorRepository = patrocinadorRepository;
    }

    @PostMapping
    public Patrocinador crear(@Valid @RequestBody Patrocinador patrocinador) {
        return patrocinadorRepository.save(patrocinador);
    }

    @GetMapping
    public List<Patrocinador> listar() {
        return patrocinadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrocinador> obtener(@PathVariable Long id) {
        return patrocinadorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patrocinador> actualizar(@PathVariable Long id, @Valid @RequestBody Patrocinador datos) {
        return patrocinadorRepository.findById(id).map(p -> {
            p.setEmpresa(datos.getEmpresa());
            p.setSector(datos.getSector());
            p.setMontoAporte(datos.getMontoAporte());
            return ResponseEntity.ok(patrocinadorRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!patrocinadorRepository.existsById(id)) return ResponseEntity.notFound().build();
        patrocinadorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Consulta 4: Patrocinadores con monto entre min y max, ordenados por monto DESC
    @GetMapping("/por-monto")
    public List<Patrocinador> porMonto(@RequestParam Double min, @RequestParam Double max) {
        return patrocinadorRepository.findByMontoAporteBetweenOrderByMontoAporteDesc(min, max);
    }
}
