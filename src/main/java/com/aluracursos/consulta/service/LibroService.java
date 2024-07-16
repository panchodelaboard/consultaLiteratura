package com.aluracursos.consulta.service;

import com.aluracursos.consulta.dto.AutorDTO;
import com.aluracursos.consulta.dto.LibroDTO;
import com.aluracursos.consulta.model.Autor;
import com.aluracursos.consulta.model.DatosLibro;
import com.aluracursos.consulta.model.Libro;
import com.aluracursos.consulta.repository.AutorRepository;
import com.aluracursos.consulta.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public LibroDTO buscarLibroPorTitulo(String titulo) {
        Libro libro = libroRepository.findByTituloContainsIgnoreCase(titulo).orElse(null);
        return libro != null ? mapLibroToDTO(libro) : null;
    }

    public List<LibroDTO> listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAllWithAutores();
        return libros.stream()
                .map(this::mapLibroToDTO)
                .collect(Collectors.toList());
    }

    public List<AutorDTO> listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        return autores.stream()
                .map(this::mapAutorToDTO)
                .collect(Collectors.toList());
    }

    public List<AutorDTO> listarAutoresVivosPorAno(int fechaDeNacimiento) {
        List<Autor> autores = autorRepository.findByFechaDeNacimiento(LocalDate.ofEpochDay(fechaDeNacimiento));
        return autores.stream()
                .map(this::mapAutorToDTO)
                .collect(Collectors.toList());
    }

    public List<LibroDTO> listarLibrosPorIdioma(String idioma) {
        List<Libro> libros = libroRepository.findAllByIdioma(idioma);
        return libros.stream()
                .map(this::mapLibroToDTO)
                .collect(Collectors.toList());
    }

    public void guardarLibro(DatosLibro datosLibro) {
        Libro libro = convertirADatosLibro(datosLibro);
        libroRepository.save(libro);
    }

private Libro convertirADatosLibro(DatosLibro datosLibro) {
    Libro libro = new Libro();
    libro.setTitulo(datosLibro.titulo());
    libro.setIdioma(datosLibro.idiomas().toString());
    libro.setNumeroDeDescargas(datosLibro.numeroDeDescargas());
    List<Autor> authors = datosLibro.autor().stream()
            .map(autorDTO -> {
                Autor autor = new Autor();
                autor.setNombre(autorDTO.nombre());
                autor.setFechaDeNacimiento(autorDTO.fechaDeNacimiento() != null && !autorDTO.fechaDeNacimiento().isEmpty()
                        ? getLocalDateFromYear(Integer.parseInt(autorDTO.fechaDeNacimiento()))
                        : null);
                autor.getLibros().add(libro);
                return autor;
            }).collect(Collectors.toList());
    libro.setAutores(authors);
    return libro;
}

private LibroDTO mapLibroToDTO(Libro libro) {
    List<AutorDTO> autoresDTO = libro.getAutores().stream()
            .map(autor -> {
                return new AutorDTO(
                        autor.getId(),
                        autor.getNombre(),
                        //autor.getFechaDeNacimiento().toString(),
                        autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento().toString() : "",

                        List.of(libro.getId())
                );
            }).collect(Collectors.toList());
    return new LibroDTO(libro.getId(), libro.getTitulo(), autoresDTO, libro.getIdioma(), libro.getNumeroDeDescargas());
}

    private AutorDTO mapAutorToDTO(Autor autor) {
        String fechaDeNacimiento = autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento().toString() : null;
        return new AutorDTO(autor.getId(), autor.getNombre(), fechaDeNacimiento, List.of());
    }

    private LocalDate getLocalDateFromYear(int year) {
        return LocalDate.of(year, 1, 1);
    }
}
