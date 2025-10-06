package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.AlimentacionDTO;

import java.util.List;

public interface IAlimentacionService {
    AlimentacionDTO crear(AlimentacionDTO dto);

    List<AlimentacionDTO> listarPorActividad(Long actividadId);

    void eliminar(Long alimentacionId);
}
