package com.alura.forohub.domain.repository;

import com.alura.forohub.domain.model.Topico;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(@NotBlank String titulo, @NotBlank String mensaje);

    @Query("""
        select t from Topico t
        where (:curso is null or lower(t.curso) = lower(:curso))
          and (:anio is null or function('year', t.fechaCreacion) = :anio)
        """)
    Page<Topico> buscarPorCursoYAnio(@Param("curso") String curso, @Param("anio") Integer anio, Pageable pageable);
}
