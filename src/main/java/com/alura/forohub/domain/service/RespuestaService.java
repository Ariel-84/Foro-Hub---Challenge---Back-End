package com.alura.forohub.domain.service;

import com.alura.forohub.domain.model.Respuesta;
import com.alura.forohub.domain.repository.RespuestaRepository;
import com.alura.forohub.domain.repository.TopicoRepository;
import com.alura.forohub.domain.repository.UsuarioRepository;
import com.alura.forohub.domain.dto.RespuestaDTO.DatosRegistroRespuesta;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    public RespuestaService(RespuestaRepository respuestaRepository,
                            TopicoRepository topicoRepository,
                            UsuarioRepository usuarioRepository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Respuesta crearRespuesta(DatosRegistroRespuesta dto) {
        var topico = topicoRepository.findById(dto.idTopico())
                .orElseThrow(() -> new EntityNotFoundException("Tópico no encontrado"));

        var autor = usuarioRepository.findById(dto.idAutor())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(dto.mensaje());
        respuesta.setTopico(topico);
        respuesta.setAutor(autor);
        respuesta.setFechaCreacion(LocalDateTime.now());
        respuesta.setSolucion(false);

        return respuestaRepository.save(respuesta);
    }
}
