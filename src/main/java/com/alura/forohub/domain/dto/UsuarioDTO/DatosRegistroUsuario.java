package com.alura.forohub.domain.dto.UsuarioDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosRegistroUsuario(
        @NotBlank String login,
        @NotBlank String nombre,
        @NotBlank @Email String email,
        @NotBlank String contrasena
) {}
