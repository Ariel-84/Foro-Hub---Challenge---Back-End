package com.alura.forohub.domain.dto.RespuestaDTO;

import com.alura.forohub.domain.model.Respuesta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DatosRespuesta(
        Long id,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaCreacion,
        String autor
) {
    public DatosRespuesta(Respuesta respuesta) {
        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getNombre()
        );
    }
}
