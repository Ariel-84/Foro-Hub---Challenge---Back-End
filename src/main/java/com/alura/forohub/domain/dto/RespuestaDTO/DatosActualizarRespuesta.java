package com.alura.forohub.domain.dto.RespuestaDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(
        @NotNull Long id,
        @NotBlank(message = "El mensaje no puede estar vacío") String mensaje
) {}
