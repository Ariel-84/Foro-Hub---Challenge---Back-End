package com.alura.forohub.controller;

import com.alura.forohub.domain.service.DatosAutenticacion;
import com.alura.forohub.infra.security.DatosTokenJWT;
import com.alura.forohub.domain.model.Usuario;
import com.alura.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity<?> iniciarSesion(@RequestBody @Valid DatosAutenticacion datos) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.contrasena());
            var autenticacion = manager.authenticate(authenticationToken);

            Usuario usuario = (Usuario) autenticacion.getPrincipal();

            var tokenJWT = tokenService.generarToken(usuario);

            return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error inesperado");
        }
    }
}