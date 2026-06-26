package com.techevent.repository;

import com.techevent.model.Ponente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PonenteRepository extends JpaRepository<Ponente, Long> {

    // Consulta 2: Buscar ponentes cuya especialidad contenga una palabra clave (ignore case)
    List<Ponente> findByEspecialidadContainingIgnoreCase(String keyword);
}
