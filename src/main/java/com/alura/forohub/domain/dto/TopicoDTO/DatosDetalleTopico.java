package com.alura.forohub.domain.dto.TopicoDTO;

import com.alura.forohub.domain.model.Topico;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
public record DatosDetalleTopico(
        String titulo,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaCreacion,
        String status,
        String autor,
        String curso
) {
    public DatosDetalleTopico(Topico topico) {
        this(
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getNombre() : null,
                topico.getCurso() != null ? topico.getCurso().getNombre() : null
        );
    }
}
