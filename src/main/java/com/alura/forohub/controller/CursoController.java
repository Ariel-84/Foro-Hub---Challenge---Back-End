package com.alura.forohub.controller;

import com.alura.forohub.domain.dto.CursoDTO.DatosCurso;
import com.alura.forohub.domain.dto.DatosActualizarCurso;

import com.alura.forohub.domain.dto.DatosRegistroCurso;
import com.alura.forohub.domain.model.Curso;
import com.alura.forohub.domain.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroCurso datos) {
        if (cursoRepository.existsByNombre(datos.nombre())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ya existe un curso con ese nombre");
        }
        Curso curso = new Curso(datos);
        cursoRepository.save(curso);
        return ResponseEntity.ok(new DatosCurso(curso));
    }

    @GetMapping
    public Page<Curso> listar(@PageableDefault(size = 10) Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> detalle(@PathVariable Long id) {
        return cursoRepository.findById(id)
                .map(curso -> ResponseEntity.ok(curso))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarCurso datos) {
        var optionalCurso = cursoRepository.findById(id);
        if (optionalCurso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso curso = optionalCurso.get();
        curso.setNombre(datos.nombre());
        curso.setCategoria(datos.categoria());
        cursoRepository.save(curso);
        return ResponseEntity.ok(new DatosCurso(curso));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var optionalCurso = cursoRepository.findById(id);
        if (optionalCurso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
