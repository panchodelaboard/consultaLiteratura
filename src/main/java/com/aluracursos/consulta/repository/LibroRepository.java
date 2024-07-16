package com.aluracursos.consulta.repository;

import com.aluracursos.consulta.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);
    List<Libro> findAllByIdioma(String idioma);

    @Query("SELECT l FROM Libro l JOIN FETCH l.autores")
    public List<Libro>findAllWithAutores();
}
