package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.ActividadesDiariasDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface  IActividadesDiariasService {
    ActividadesDiariasDTO crearOObtener(Long usuarioId, LocalDate fecha, String descripcion,
                                        Long familiaId, Long institucionId);

    Optional<ActividadesDiariasDTO> obtenerPorId(Long actividadId);

    Optional<ActividadesDiariasDTO> obtenerPorUsuarioYFecha(Long usuarioId, LocalDate fecha);

    List<ActividadesDiariasDTO> listarPorUsuarioYRango(Long usuarioId, LocalDate desde, LocalDate hasta);

    void eliminar(Long actividadId);
}
