package com.alura.forohub.domain.dto.TopicoDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroTopico(
        @NotNull Long autorId,
        @NotNull Long cursoId,
        @NotBlank String titulo,
        @NotBlank String mensaje
) {}
