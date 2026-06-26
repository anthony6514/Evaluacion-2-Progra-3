package com.techevent.controller;

import com.techevent.model.PerfilPonente;
import com.techevent.repository.PerfilPonenteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfiles")
@CrossOrigin(origins = "*")
public class PerfilPonenteController {

    private final PerfilPonenteRepository perfilRepository;

    public PerfilPonenteController(PerfilPonenteRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    @PostMapping
    public PerfilPonente crear(@Valid @RequestBody PerfilPonente perfil) {
        return perfilRepository.save(perfil);
    }

    @GetMapping
    public List<PerfilPonente> listar() {
        return perfilRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilPonente> obtener(@PathVariable Long id) {
        return perfilRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilPonente> actualizar(@PathVariable Long id, @Valid @RequestBody PerfilPonente datos) {
        return perfilRepository.findById(id).map(p -> {
            p.setBiografia(datos.getBiografia());
            p.setLinkedin(datos.getLinkedin());
            p.setPaginaWeb(datos.getPaginaWeb());
            p.setPonente(datos.getPonente());
            return ResponseEntity.ok(perfilRepository.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!perfilRepository.existsById(id)) return ResponseEntity.notFound().build();
        perfilRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
