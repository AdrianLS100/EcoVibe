package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.FamiliaDTO;
import com.upc.ecovibe.entities.Familia;

import java.util.List;
import java.util.Optional;

public interface IFamiliaService {
    FamiliaDTO crear(Long ownerUserId, String nombre);
    List<FamiliaDTO> listarPorOwner(Long ownerUserId);

    Optional<FamiliaDTO> obtener(Long familiaId);

    Familia getRef(Long familiaId);
}
