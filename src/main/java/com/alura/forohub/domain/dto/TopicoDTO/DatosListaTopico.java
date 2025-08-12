package com.alura.forohub.domain.dto.TopicoDTO;

import com.alura.forohub.domain.model.Topico;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DatosListaTopico(
        Long id,
        String titulo,
        String mensaje,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime fechaCreacion,
        String status,
        String autor,
        String curso
) {
    public DatosListaTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor() != null ? topico.getAutor().getNombre() : "Sin autor",
                topico.getCurso() != null ? topico.getCurso().getNombre() : "Sin curso"
        );
    }
}
