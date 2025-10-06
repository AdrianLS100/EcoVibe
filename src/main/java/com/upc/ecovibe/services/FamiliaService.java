// src/main/java/com/upc/ecovibe/services/FamiliaService.java
package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.FamiliaDTO;
import com.upc.ecovibe.entities.Familia;
import com.upc.ecovibe.interfaces.IFamiliaService;
import com.upc.ecovibe.repositories.FamiliaRepository;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class FamiliaService implements IFamiliaService {

    private final FamiliaRepository familiaRepo;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    public FamiliaService(FamiliaRepository f, UserRepository u, ModelMapper m) {
        this.familiaRepo = f; this.userRepo = u; this.mapper = m;
    }

    @Override
    public FamiliaDTO crear(Long ownerUserId, String nombre) {
        var owner = userRepo.findById(ownerUserId)
                .orElseThrow(() -> new NoSuchElementException("Owner no existe: " + ownerUserId));
        var f = new Familia();
        f.setNombre(nombre);
        f.setOwner(owner);
        return mapper.map(familiaRepo.save(f), FamiliaDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamiliaDTO> listarPorOwner(Long ownerUserId) {
        return familiaRepo.findByOwner_Id(ownerUserId).stream()
                .map(x -> mapper.map(x, FamiliaDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FamiliaDTO> obtener(Long familiaId) {
        return familiaRepo.findById(familiaId).map(x -> mapper.map(x, FamiliaDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Familia getRef(Long familiaId) {
        return familiaRepo.findById(familiaId)
                .orElseThrow(() -> new NoSuchElementException("Familia no existe: " + familiaId));
    }
}
