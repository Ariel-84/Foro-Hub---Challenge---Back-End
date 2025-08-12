# ForoHub

**ForoHub** es una API RESTful desarrollada en Java con Spring Boot que proporciona una plataforma segura, eficiente y escalable para la gestión de foros en línea.  
Esta aplicación incorpora autenticación basada en JSON Web Tokens (JWT) para garantizar la seguridad en el acceso a sus recursos.

---

## Tecnologías y herramientas utilizadas

- **Java 17+**  
- **Spring Boot 3+**  
- **Spring Security con JWT** para autenticación y autorización  
- **MySQL 8+** como sistema gestor de bases de datos  
- **Flyway** para gestión y versionado de migraciones  
- **Lombok** para reducción de código repetitivo  
- **Maven** para gestión de dependencias y construcción del proyecto  

---

## Características principales

### Gestión de usuarios  
- Entidad `Usuario` con atributos: `id`, `login`, `nombre`, `email` y `contrasena` cifrada con BCrypt.  
- Asociación `ManyToMany` con entidad `Perfil` para soporte futuro de roles y perfiles de usuario.

### Entidades del foro  
- Gestión de `Tópicos` y `Respuestas` con relaciones claras a usuarios y cursos.  
- Funcionalidad CRUD completa para los tópicos.

### Seguridad y autenticación  
- Autenticación basada en JWT:  
  - Endpoint `/login` para generación de tokens firmados con tiempo de expiración configurable.  
  - Servicio `TokenService` para creación y validación de tokens.  
  - Filtro `SecurityFilter` que valida tokens en cada solicitud y establece contexto de seguridad.  
- Configuración de seguridad con:  
  - CSRF deshabilitado para APIs REST.  
  - Sesiones stateless para escalabilidad y compatibilidad con JWT.  
  - Acceso abierto a endpoints de login y registro.  
  - Protección del resto de recursos con autenticación requerida.  
  - `PasswordEncoder` con BCrypt.

### Paginación y filtrado  
- Endpoints de listados y búsquedas de tópicos que soportan paginación y ordenamiento mediante parámetros `page`, `size` y `sort`.  
- Método de búsqueda avanzado que permite filtrar por curso y año, aplicando paginación.

### Manejo de errores  
- Control centralizado para manejar excepciones y devolver respuestas consistentes y claras.

---

## Endpoints disponibles

### Autenticación y usuarios  
| Método | URL             | Descripción                                        |  
|--------|-----------------|--------------------------------------------------|  
| POST   | `/login`        | Autenticación: recibe login y contraseña, devuelve token JWT. |  
| POST   | `/usuarios`     | Registro de nuevo usuario.                         |  
| GET    | `/usuarios`     | Listar usuarios (puede requerir rol admin, si se implementa). |  
| GET    | `/usuarios/{id}`| Obtener detalle de usuario específico.            |  
| PUT    | `/usuarios/{id}`| Actualizar datos del usuario.                      |  
| DELETE | `/usuarios/{id}`| Eliminar usuario (generalmente para admin).       |  

### Tópicos  
| Método | URL               | Descripción                                      |  
|--------|-------------------|------------------------------------------------|  
| GET    | `/topicos`        | Listar tópicos con paginación y ordenamiento.  |  
| GET    | `/topicos/buscar` | Buscar tópicos filtrando por curso y año, con paginación. |  
| GET    | `/topicos/{id}`   | Obtener detalle de un tópico específico.         |  
| POST   | `/topicos`        | Crear un nuevo tópico.                           |  
| PUT    | `/topicos/{id}`   | Actualizar un tópico existente.                  |  
| DELETE | `/topicos/{id}`   | Eliminar un tópico.                              |  

### Respuestas  
| Método | URL                 | Descripción                                    |  
|--------|---------------------|------------------------------------------------|  
| POST   | `/respuestas`       | Crear una respuesta a un tópico.                |  
| GET    | `/respuestas/{id}`  | Obtener detalle de una respuesta.               |  
| PUT    | `/respuestas/{id}`  | Actualizar una respuesta.                        |  
| DELETE | `/respuestas/{id}`  | Eliminar una respuesta.                          |  

---

## Funcionalidades en desarrollo

- **Gestión granular de roles y permisos:**  
  - Definición y asignación de perfiles (`ROLE_USER`, `ROLE_ADMIN`).  
  - Control de autorización por roles para restringir operaciones.  
  - Validación para que usuarios modifiquen o eliminen solo sus propios recursos.

- **Mejoras en seguridad:**  
  - Implementación de refresh tokens.  
  - Mejor manejo de errores de autenticación y autorización.

- **Extensiones funcionales:**  
  - Documentación automatizada con Swagger/OpenAPI.  
  - Validaciones adicionales y robustecimiento de DTOs.

---

## Autor

Ariel Caferri

---
