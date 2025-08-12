package com.alura.forohub.controller;

import com.alura.forohub.domain.model.Respuesta;
import com.alura.forohub.domain.model.Topico;
import com.alura.forohub.domain.model.Usuario;
import com.alura.forohub.domain.repository.RespuestaRepository;
import com.alura.forohub.domain.repository.TopicoRepository;
import com.alura.forohub.domain.repository.UsuarioRepository;
import com.alura.forohub.domain.dto.RespuestaDTO.DatosActualizarRespuesta;
import com.alura.forohub.domain.dto.RespuestaDTO.DatosRegistroRespuesta;
import com.alura.forohub.domain.dto.RespuestaDTO.DatosRespuesta;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroRespuesta datos) {
        Topico topico = topicoRepository.findById(datos.idTopico()).orElse(null);
        Usuario autor = usuarioRepository.findById(datos.idAutor()).orElse(null);

        if (topico == null || autor == null) {
            return ResponseEntity.badRequest().body("Tópico o autor no encontrados.");
        }

        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(datos.mensaje());
        respuesta.setFechaCreacion(LocalDateTime.now());
        respuesta.setTopico(topico);
        respuesta.setAutor(autor);
        respuesta.setSolucion(false);

        respuestaRepository.save(respuesta);
        return ResponseEntity.ok(new DatosRespuesta(respuesta));
    }

    @GetMapping("/topico/{topicoId}")
    public ResponseEntity<List<DatosRespuesta>> listarPorTopico(@PathVariable Long topicoId) {
        List<Respuesta> respuestas = respuestaRepository.findByTopicoId(topicoId);
        List<DatosRespuesta> datos = respuestas.stream()
                .map(DatosRespuesta::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(datos);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> actualizar(@RequestBody @Valid DatosActualizarRespuesta datos) {
        Respuesta respuesta = respuestaRepository.findById(datos.id()).orElse(null);

        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }

        respuesta.setMensaje(datos.mensaje());
        return ResponseEntity.ok(new DatosRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!respuestaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }


}
