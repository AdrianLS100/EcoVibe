package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.FactoresEmisionDTO;

import java.util.List;
import java.util.Optional;

public interface IFactoresEmisionService {
    FactoresEmisionDTO crear(FactoresEmisionDTO dto);

    FactoresEmisionDTO actualizar(Long id, FactoresEmisionDTO dto);

    Optional<FactoresEmisionDTO> obtener(Long id);

    Optional<FactoresEmisionDTO> buscarVigente(String categoria, String subcategoria, String unidadBase);

    List<FactoresEmisionDTO> listarVigentesPorCategoria(String categoria);

    void eliminar(Long id);
}
