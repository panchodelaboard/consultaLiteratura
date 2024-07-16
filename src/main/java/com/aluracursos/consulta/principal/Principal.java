package com.aluracursos.consulta.principal;

import com.aluracursos.consulta.model.*;
import com.aluracursos.consulta.service.ConsumoAPI;
import com.aluracursos.consulta.service.ConvierteDatos;
import com.aluracursos.consulta.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Principal implements CommandLineRunner {


    @Autowired
    private final LibroService libroService;

    @Autowired
    public Principal(LibroService libroService) {
        this.libroService = libroService;
    }

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/?search=";

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    Elije la opción a través de su número:
                    1 - Buscar libros por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos de un determinado año
                    5 - Listar libros por idiomas
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdiomas();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Escribe el título del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "+"));
        var datos = conversor.obtenerDatos(json, Datos.class);
        var libros = datos.resultados();

        if (!libros.isEmpty()) {
            var libro = libros.get(0); // Get the first book
            System.out.println("Datos del Libro:");
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autores:");
            for (var autor : libro.autor()) {
                System.out.println("  - " + autor.nombre() + " (" + autor.fechaDeNacimiento() + ")");
            }
            System.out.println("Idiomas: " + libro.idiomas());
            System.out.println("Número de Descargas: " + libro.numeroDeDescargas());

            // Guardar el libro en la base de datos
            libroService.guardarLibro(libro);

        } else {
            System.out.println("No se encontraron resultados para la búsqueda.");
        }
    }


    private void listarLibrosRegistrados() {
        var libros = libroService.listarLibrosRegistrados();
        libros.forEach(libro -> {
            System.out.println("ID: " + libro.id());
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autores: " + libro.autores());
            System.out.println("Idiomas: " + libro.idiomas());
            System.out.println("Número de Descargas: " + libro.numeroDeDescargas());
            System.out.println("----");
        });
    }

    private void listarAutoresRegistrados() {
        var autores = libroService.listarAutoresRegistrados();
        autores.forEach(autor -> {
            System.out.println("ID: " + autor.id());
            System.out.println("Nombre: " + autor.nombre());
            System.out.println("Fecha de Nacimiento: " + autor.fechaDeNacimiento());
            System.out.println("----");
        });
    }

    private void listarAutoresVivos() {
        System.out.println("Escribe el año para listar los autores vivos:");
        var ano = teclado.nextInt();
        teclado.nextLine();
        var autores = libroService.listarAutoresVivosPorAno(ano);
        autores.forEach(autor -> {
            System.out.println("ID: " + autor.id());
            System.out.println("Nombre: " + autor.nombre());
            System.out.println("Fecha de Nacimiento: " + autor.fechaDeNacimiento());
            System.out.println("----");
        });
    }

    private void listarLibrosPorIdiomas() {
        System.out.println("Escribe el idioma para listar los libros:");
        var idioma = teclado.nextLine();
        var libros = libroService.listarLibrosPorIdioma(idioma);
        libros.forEach(libro -> {
            System.out.println("ID: " + libro.id());
            System.out.println("Título: " + libro.titulo());
            System.out.println("Autores: " + libro.autores());
            System.out.println("Idiomas: " + libro.idiomas());
            System.out.println("Número de Descargas: " + libro.numeroDeDescargas());
            System.out.println("----");
        });
    }

    @Override
    public void run(String... args) throws Exception {
        muestraElMenu();
    }
}
