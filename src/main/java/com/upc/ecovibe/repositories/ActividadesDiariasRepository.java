package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.ActividadesDiarias;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActividadesDiariasRepository extends JpaRepository<ActividadesDiarias, Long> {

    Optional<ActividadesDiarias> findByUsuario_IdAndFecha(Long usuarioId, LocalDate fecha);

    List<ActividadesDiarias> findByUsuario_IdAndFechaBetween(Long usuarioId, LocalDate desde, LocalDate hasta);

}
