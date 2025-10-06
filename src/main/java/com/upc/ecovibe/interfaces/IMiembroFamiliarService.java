package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.MiembroFamiliarDTO;
import com.upc.ecovibe.entities.MiembroFamiliar;

import java.util.List;
import java.util.Optional;

public interface IMiembroFamiliarService {
    MiembroFamiliarDTO crear(Long familiaId, String nombre, String relacion, Long userId);
    List<MiembroFamiliarDTO> listar(Long familiaId);

    Optional<MiembroFamiliarDTO> obtener(Long miembroId);

    MiembroFamiliar getRef(Long miembroId);
    void validarMiembroEnFamilia(Long familiaId, Long miembroId);
}
