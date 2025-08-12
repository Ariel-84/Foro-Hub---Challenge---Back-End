package com.alura.forohub.domain.dto.UsuarioDTO;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DatosActualizarUsuario(
        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "Formato de email inválido")
        String email
) {}
