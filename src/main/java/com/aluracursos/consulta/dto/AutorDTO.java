package com.aluracursos.consulta.dto;

import java.util.List;

public record AutorDTO(
        Long id,
        String nombre,
        String fechaDeNacimiento,
        List<Long> libros) {
}
