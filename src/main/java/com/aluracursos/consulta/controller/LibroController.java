package com.aluracursos.consulta.controller;

import com.aluracursos.consulta.dto.AutorDTO;
import com.aluracursos.consulta.dto.LibroDTO;
import com.aluracursos.consulta.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {
    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping("/buscar")
    public LibroDTO buscarLibroPorTitulo(@RequestParam String titulo) {
        return libroService.buscarLibroPorTitulo(titulo);
    }

    @GetMapping("/listar")
    public List<LibroDTO> listarLibrosRegistrados() {
        return libroService.listarLibrosRegistrados();
    }

    @GetMapping("/autores")
    public List<AutorDTO> listarAutoresRegistrados() {
        return libroService.listarAutoresRegistrados();
    }

    @GetMapping("/autores/vivos")
    public List<AutorDTO> listarAutoresVivosPorAno(@RequestParam int ano) {
        return libroService.listarAutoresVivosPorAno(ano);
    }

    @GetMapping("/idiomas")
    public List<LibroDTO> listarLibrosPorIdioma(@RequestParam String idioma) {
        return libroService.listarLibrosPorIdioma(idioma);
    }
}
