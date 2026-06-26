package com.techevent.controller;

import com.techevent.model.Ponente;
import com.techevent.repository.PonenteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ponentes")
@CrossOrigin(origins = "*")
public class PonenteController {

    @Autowired
    private PonenteRepository ponenteRepository;

    @PostMapping
    public Ponente crear(@Valid @RequestBody Ponente ponente) {
        return ponenteRepository.save(ponente);
    }

    @GetMapping
    public List<Ponente> listar() {
        return ponenteRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ponente obtener(@PathVariable Long id) {
        Optional<Ponente> resultado = ponenteRepository.findById(id);
        if (resultado.isPresent()) {
            return resultado.get();
        }
        return null;
    }

    @PutMapping("/{id}")
    public Ponente actualizar(@PathVariable Long id, @Valid @RequestBody Ponente datos) {
        Optional<Ponente> resultado = ponenteRepository.findById(id);
        if (resultado.isPresent()) {
            Ponente ponente = resultado.get();
            ponente.setNombre(datos.getNombre());
            ponente.setEspecialidad(datos.getEspecialidad());
            ponente.setEmail(datos.getEmail());
            ponente.setAnosExperiencia(datos.getAnosExperiencia());
            return ponenteRepository.save(ponente);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (ponenteRepository.existsById(id)) {
            ponenteRepository.deleteById(id);
            return "Ponente eliminado";
        }
        return "No encontrado";
    }

    // Consulta 2: buscar por especialidad
    @GetMapping("/buscar")
    public List<Ponente> buscarPorEspecialidad(@RequestParam String keyword) {
        return ponenteRepository.findByEspecialidadContainingIgnoreCase(keyword);
    }
}
