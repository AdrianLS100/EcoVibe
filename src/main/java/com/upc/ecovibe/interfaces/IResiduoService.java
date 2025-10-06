package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.ResiduoDTO;

import java.util.List;

public interface IResiduoService {
    ResiduoDTO crear(ResiduoDTO dto);
    List<ResiduoDTO> listarPorActividad(Long actividadId);
    void eliminar(Long residuoId);
}
