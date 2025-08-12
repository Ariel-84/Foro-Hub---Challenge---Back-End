package com.alura.forohub.domain.dto.CursoDTO;

import com.alura.forohub.domain.model.Curso;

public record DatosCurso(
        Long id,
        String nombre
) {
    public DatosCurso(Curso curso) {
        this(
                curso.getId(),
                curso.getNombre()
        );
    }
}
