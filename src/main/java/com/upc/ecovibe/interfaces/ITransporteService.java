package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.TransporteDTO;

import java.math.BigDecimal;
import java.util.List;

public interface ITransporteService {
    TransporteDTO crear(TransporteDTO dto);

    List<TransporteDTO> listarPorActividad(Long actividadId);

    BigDecimal sumarDistanciaKm(Long actividadId);

    void eliminar(Long transporteId);

    long eliminarPorActividad(Long actividadId);
}
