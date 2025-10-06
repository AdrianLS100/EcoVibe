package com.upc.ecovibe.repositories;

import com.upc.ecovibe.entities.HistorialPuntos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistorialPuntosRepository extends JpaRepository<HistorialPuntos, Long> {

    @Query("select coalesce(sum(h.puntos),0) from HistorialPuntos h where h.usuarioId = :usuarioId")
    int saldoPuntos(Long usuarioId);
}
