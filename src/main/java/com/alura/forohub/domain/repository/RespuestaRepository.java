package com.alura.forohub.domain.repository;

import com.alura.forohub.domain.model.Respuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    List<Respuesta> findByTopicoId(Long topicoId);

}
