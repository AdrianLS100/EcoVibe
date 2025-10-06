package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.TransporteDTO;

import java.util.List;

public interface ITransporteService {
    TransporteDTO crear(TransporteDTO dto);
    List<TransporteDTO> listarPorActividad(Long actividadId);
    void eliminar(Long transporteId);
}
