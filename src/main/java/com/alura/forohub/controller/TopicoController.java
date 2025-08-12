package com.alura.forohub.controller;

import com.alura.forohub.domain.model.Topico;
import com.alura.forohub.domain.model.Usuario;
import com.alura.forohub.domain.model.Curso;
import com.alura.forohub.domain.repository.TopicoRepository;
import com.alura.forohub.domain.repository.UsuarioRepository;
import com.alura.forohub.domain.repository.CursoRepository;
import com.alura.forohub.domain.dto.TopicoDTO.DatosActualizarTopico;
import com.alura.forohub.domain.dto.TopicoDTO.DatosDetalleTopico;
import com.alura.forohub.domain.dto.TopicoDTO.DatosListaTopico;
import com.alura.forohub.domain.dto.TopicoDTO.DatosRegistroTopico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        boolean existe = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existe) {
            return ResponseEntity.badRequest().body("Tópico duplicado");
        }

        Optional<Usuario> autorOpt = usuarioRepository.findById(datos.autorId());
        Optional<Curso> cursoOpt = cursoRepository.findById(datos.cursoId());

        if (autorOpt.isEmpty() || cursoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Autor o curso no encontrados.");
        }

        Usuario autor = autorOpt.get();
        Curso curso = cursoOpt.get();

        Topico topico = new Topico(datos, autor, curso);
        repository.save(topico);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public Page<DatosListaTopico> listar(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion) {
        return repository.findAll(paginacion).map(DatosListaTopico::new);
    }

    @GetMapping("/buscar")
    public Page<DatosListaTopico> buscar(
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer anio,
            @PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable paginacion
    ) {
        return repository.buscarPorCursoYAnio(curso, anio, paginacion).map(DatosListaTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detalle(@PathVariable Long id) {
        var topico = repository.findById(id);
        return topico.map(value -> ResponseEntity.ok(new DatosDetalleTopico(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid DatosActualizarTopico datos
    ) {
        var optionalTopico = repository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Topico topico = optionalTopico.get();

        boolean duplicado = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (duplicado) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }

        topico.setTitulo(datos.titulo());
        topico.setMensaje(datos.mensaje());

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var topico = repository.findById(id);
        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
