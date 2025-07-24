package com.alura.forohub.domain.model;

import com.alura.forohub.dto.DatosRegistroTopico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;


@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String mensaje;

    private LocalDateTime fechaCreacion;

    private String status;

    private String autor;

    private String curso;

    public Topico(DatosRegistroTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = "NO_RESPONDIDO";
        this.autor = datos.autor();
        this.curso = datos.curso();
    }

    public void setTitulo(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    public void setMensaje(@NotBlank String mensaje) {
        this.mensaje = mensaje;
    }

}
