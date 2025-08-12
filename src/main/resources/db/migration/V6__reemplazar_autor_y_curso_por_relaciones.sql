-- Eliminamos las columnas incorrectas
ALTER TABLE topicos DROP COLUMN autor;
ALTER TABLE topicos DROP COLUMN curso;

-- Agregamos las columnas correctas para relaciones
ALTER TABLE topicos ADD COLUMN autor_id BIGINT;
ALTER TABLE topicos ADD COLUMN curso_id BIGINT;

-- Creamos las claves foráneas
ALTER TABLE topicos
ADD CONSTRAINT fk_topico_autor
FOREIGN KEY (autor_id) REFERENCES usuarios(id);

ALTER TABLE topicos
ADD CONSTRAINT fk_topico_curso
FOREIGN KEY (curso_id) REFERENCES cursos(id);
