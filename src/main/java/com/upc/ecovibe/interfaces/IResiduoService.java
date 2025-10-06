package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.ResiduoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IResiduoService {
    ResiduoDTO crear(ResiduoDTO dto);

    List<ResiduoDTO> listarPorActividad(Long actividadId);

    BigDecimal sumarPesoKg(Long actividadId);

    void eliminar(Long residuoId);

    long eliminarPorActividad(Long actividadId);
}
