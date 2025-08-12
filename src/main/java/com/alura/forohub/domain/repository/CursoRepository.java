package com.alura.forohub.domain.repository;

import com.alura.forohub.domain.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    // Método para chequear si existe un curso con ese nombre (devuelve true o false)
    boolean existsByNombre(String nombre);

    // método para buscar por nombre
    Curso findByNombre(String nombre);
}
