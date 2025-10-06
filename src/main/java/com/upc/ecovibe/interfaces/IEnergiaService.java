package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.EnergiaDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IEnergiaService {
    EnergiaDTO crear(EnergiaDTO dto);

    List<EnergiaDTO> listarPorActividad(Long actividadId);

    BigDecimal sumarConsumo(Long actividadId);

    void eliminar(Long energiaId);

    long eliminarPorActividad(Long actividadId);
}
