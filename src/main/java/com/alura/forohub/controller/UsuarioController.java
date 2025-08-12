package com.alura.forohub.controller;

import com.alura.forohub.domain.dto.UsuarioDTO.DatosActualizarUsuario;
import com.alura.forohub.domain.dto.UsuarioDTO.DatosRegistroUsuario;
import com.alura.forohub.domain.dto.UsuarioDTO.DatosUsuario;
import com.alura.forohub.domain.model.Usuario;
import com.alura.forohub.domain.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosUsuario> registrar(@RequestBody @Valid DatosRegistroUsuario datos) {
        String contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());
        Usuario usuario = new Usuario(
                datos.login(),
                contrasenaEncriptada,
                datos.nombre(),
                datos.email()
        );
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new DatosUsuario(usuario));
    }


    @GetMapping
    public Page<DatosUsuario> listar(@PageableDefault(size = 10) Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(DatosUsuario::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosUsuario> detalle(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(new DatosUsuario(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosUsuario> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarUsuario datos) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.actualizarDatos(datos.nombre(), datos.email());
                    usuarioRepository.save(usuario);  // guardar cambios
                    return ResponseEntity.ok(new DatosUsuario(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
