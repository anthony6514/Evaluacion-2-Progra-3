package com.techevent.controller;

import com.techevent.model.Patrocinador;
import com.techevent.repository.PatrocinadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patrocinadores")
@CrossOrigin(origins = "*")
public class PatrocinadorController {

    @Autowired
    private PatrocinadorRepository patrocinadorRepository;

    @PostMapping
    public Patrocinador crear(@Valid @RequestBody Patrocinador patrocinador) {
        return patrocinadorRepository.save(patrocinador);
    }

    @GetMapping
    public List<Patrocinador> listar() {
        return patrocinadorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Patrocinador obtener(@PathVariable Long id) {
        Optional<Patrocinador> resultado = patrocinadorRepository.findById(id);
        if (resultado.isPresent()) {
            return resultado.get();
        }
        return null;
    }

    @PutMapping("/{id}")
    public Patrocinador actualizar(@PathVariable Long id, @Valid @RequestBody Patrocinador datos) {
        Optional<Patrocinador> resultado = patrocinadorRepository.findById(id);
        if (resultado.isPresent()) {
            Patrocinador patrocinador = resultado.get();
            patrocinador.setEmpresa(datos.getEmpresa());
            patrocinador.setSector(datos.getSector());
            patrocinador.setMontoAporte(datos.getMontoAporte());
            return patrocinadorRepository.save(patrocinador);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (patrocinadorRepository.existsById(id)) {
            patrocinadorRepository.deleteById(id);
            return "Patrocinador eliminado";
        }
        return "No encontrado";
    }

    // Consulta 4: patrocinadores por rango de monto
    @GetMapping("/por-monto")
    public List<Patrocinador> porMonto(@RequestParam Double min, @RequestParam Double max) {
        return patrocinadorRepository.findByMontoAporteBetweenOrderByMontoAporteDesc(min, max);
    }
}
