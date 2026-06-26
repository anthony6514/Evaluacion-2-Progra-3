package com.techevent.controller;

import com.techevent.model.Ponente;
import com.techevent.repository.PonenteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponentes")
@CrossOrigin(origins = "*")
public class PonenteController {

    private final PonenteRepository ponenteRepository;

    public PonenteController(PonenteRepository ponenteRepository) {
        this.ponenteRepository = ponenteRepository;
    }

    // Crear
    @PostMapping
    public Ponente crear(@Valid @RequestBody Ponente ponente) {
        return ponenteRepository.save(ponente);
    }

    // Listar todos
    @GetMapping
    public List<Ponente> listar() {
        return ponenteRepository.findAll();
    }

    // Obtener por id
    @GetMapping("/{id}")
    public ResponseEntity<Ponente> obtener(@PathVariable Long id) {
        return ponenteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Ponente> actualizar(@PathVariable Long id, @Valid @RequestBody Ponente datos) {
        return ponenteRepository.findById(id).map(p -> {
            p.setNombre(datos.getNombre());
            p.setEspecialidad(datos.getEspecialidad());
            p.setEmail(datos.getEmail());
            p.setAnosExperiencia(datos.getAnosExperiencia());
            return ResponseEntity.ok(ponenteRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!ponenteRepository.existsById(id)) return ResponseEntity.notFound().build();
        ponenteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Consulta 2: Buscar por especialidad (palabra clave)
    @GetMapping("/buscar")
    public List<Ponente> buscarPorEspecialidad(@RequestParam String keyword) {
        return ponenteRepository.findByEspecialidadContainingIgnoreCase(keyword);
    }
}
