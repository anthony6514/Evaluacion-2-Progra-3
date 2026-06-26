package com.techevent.controller;

import com.techevent.model.PerfilPonente;
import com.techevent.repository.PerfilPonenteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfiles")
@CrossOrigin(origins = "*")
public class PerfilPonenteController {

    @Autowired
    private PerfilPonenteRepository perfilRepository;

    @PostMapping
    public PerfilPonente crear(@Valid @RequestBody PerfilPonente perfil) {
        return perfilRepository.save(perfil);
    }

    @GetMapping
    public List<PerfilPonente> listar() {
        return perfilRepository.findAll();
    }

    @GetMapping("/{id}")
    public PerfilPonente obtener(@PathVariable Long id) {
        Optional<PerfilPonente> resultado = perfilRepository.findById(id);
        if (resultado.isPresent()) {
            return resultado.get();
        }
        return null;
    }

    @PutMapping("/{id}")
    public PerfilPonente actualizar(@PathVariable Long id, @Valid @RequestBody PerfilPonente datos) {
        Optional<PerfilPonente> resultado = perfilRepository.findById(id);
        if (resultado.isPresent()) {
            PerfilPonente perfil = resultado.get();
            perfil.setBiografia(datos.getBiografia());
            perfil.setLinkedin(datos.getLinkedin());
            perfil.setPaginaWeb(datos.getPaginaWeb());
            perfil.setPonente(datos.getPonente());
            return perfilRepository.save(perfil);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        if (perfilRepository.existsById(id)) {
            perfilRepository.deleteById(id);
            return "Perfil eliminado";
        }
        return "No encontrado";
    }
}
