package com.alura.forohub.domain.repository;

import com.alura.forohub.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByNombre(String nombre);

    Optional<Usuario> findByLogin(String login);

}
