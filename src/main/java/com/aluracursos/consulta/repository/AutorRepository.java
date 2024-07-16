package com.aluracursos.consulta.repository;

import com.aluracursos.consulta.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findByFechaDeNacimiento(LocalDate fechaDeNacimiento);
}
