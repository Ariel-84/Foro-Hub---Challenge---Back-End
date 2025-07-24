package com.alura.forohub.controller;

import com.alura.forohub.domain.model.Topico;
import com.alura.forohub.domain.repository.TopicoRepository;
import com.alura.forohub.dto.DatosActualizarTopico;
import com.alura.forohub.dto.DatosDetalleTopico;
import com.alura.forohub.dto.DatosListaTopico;
import com.alura.forohub.dto.DatosRegistroTopico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Transactional
    @PostMapping
    public void registrar(@RequestBody @Valid DatosRegistroTopico datos) {
        // Evitar duplicados.
        boolean existe = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existe) {
            throw new IllegalArgumentException("Tópico duplicado");
        }
        repository.save(new Topico(datos));
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
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
        }
        return ResponseEntity.notFound().build();
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

        // Verificar si ya existe otro tópico con mismo título y mensaje
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
