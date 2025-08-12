package com.alura.forohub.domain.model;

import com.alura.forohub.domain.dto.UsuarioDTO.DatosRegistroUsuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;

    private String nombre;

    @Column(name = "email", unique = true)
    private String email;

    private String contrasena;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_perfiles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "perfil_id")
    )
    private List<Perfil> perfiles;

    // Constructor desde DTO
    public Usuario(@Valid DatosRegistroUsuario datos) {
        this.login = datos.login();
        this.nombre = datos.nombre();
        this.email = datos.email();
        this.contrasena = datos.contrasena();
    }

    // Constructor sin ID ni perfiles (útil para testeo o servicios)
    public Usuario(String login, String contrasena, String nombre, String email) {
        this.login = login;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.email = email;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfiles != null
                ? perfiles.stream()
                .map(perfil -> new SimpleGrantedAuthority(perfil.getNombre()))
                .collect(Collectors.toList())
                : List.of();
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizarDatos(String nombre, String email) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        }

        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        }
    }
}
