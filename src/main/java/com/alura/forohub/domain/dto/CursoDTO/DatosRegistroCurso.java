package com.alura.forohub.domain.dto;

import com.alura.forohub.domain.model.Categoria;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroCurso(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "La categoría es obligatoria")
        Categoria categoria
) {}
