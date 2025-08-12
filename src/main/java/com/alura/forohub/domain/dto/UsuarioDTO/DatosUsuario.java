package com.alura.forohub.domain.dto.UsuarioDTO;

import com.alura.forohub.domain.model.Usuario;

public record DatosUsuario(
        Long id,
        String nombre,
        String email //
) {
    public DatosUsuario(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail()
        );
    }
}
