# Consulta de Libros

Aplicación de consola en Java usando Spring Boot para buscar y guardar información de libros.

## Características

- Buscar libros por título mediante una API externa.
- Listar libros guardados.
- Listar autores registrados.
- Listar autores vivos por año de nacimiento.
- Listar libros por idioma.

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL

## Requisitos Previos

- Java 17
- PostgreSQL
- IDE (IntelliJ IDEA recomendado)

## Configuración del Proyecto

1. Clona el repositorio:
    ```sh
    git clone https://github.com/tu-usuario/consultaLibros.git
    ```

2. Configura PostgreSQL:
    - Crea una base de datos `consulta_libros`.
    - Asegúrate de que las credenciales en `application.properties` sean correctas.

3. Configura `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/consulta_libros
    spring.datasource.username=tu_usuario
    spring.datasource.password=tu_contraseña
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

4. Construye y ejecuta el proyecto:
    ```sh
    ./mvnw clean install
    ./mvnw spring-boot:run
    ```

## Uso

Ejecuta la aplicación y usa el menú en consola para:

1. Buscar libro por título.
2. Listar libros registrados.
3. Listar autores registrados.
4. Listar autores vivos por año de nacimiento.
5. Listar libros por idioma.
6. Salir.

## Estructura del Proyecto

```plaintext
consultaLibros/
├── src/
│   ├── main/
│   │   ├── java/com/aluracursos/consulta/
│   │   │   ├── model/       # Clases de modelo
│   │   │   ├── dto/         # Clases DTO
│   │   │   ├── repository/  # Interfaces de repositorio
│   │   │   ├── service/     # Clases de servicio
│   │   │   ├── Principal.java # Clase principal
│   ├── resources/
│   │   ├── application.properties # Configuración
├── pom.xml     # Configuración de Maven
└── README.md   # Este archivo
