package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.MiembroFamiliarDTO;
import com.upc.ecovibe.entities.MiembroFamiliar;
import com.upc.ecovibe.interfaces.IFamiliaService;
import com.upc.ecovibe.interfaces.IMiembroFamiliarService;
import com.upc.ecovibe.repositories.MiembroFamiliarRepository;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class MiembroFamiliarService implements IMiembroFamiliarService {

    private final MiembroFamiliarRepository miembroRepo;
    private final IFamiliaService familiaService;
    private final UserRepository userRepo;
    private final ModelMapper mapper;

    public MiembroFamiliarService(MiembroFamiliarRepository m,
                                  IFamiliaService f,
                                  UserRepository u,
                                  ModelMapper mm) {
        this.miembroRepo = m; this.familiaService = f; this.userRepo = u; this.mapper = mm;
    }

    @Override
    public MiembroFamiliarDTO crear(Long familiaId, String nombre, String relacion, Long userId) {
        var fam = familiaService.getRef(familiaId);
        var mf = new MiembroFamiliar();
        mf.setFamilia(fam);
        mf.setNombre(nombre);
        mf.setRelacion(relacion);
        if (userId != null) {
            var usr = userRepo.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User no existe: " + userId));
            mf.setUser(usr);
        }
        return mapper.map(miembroRepo.save(mf), MiembroFamiliarDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MiembroFamiliarDTO> listar(Long familiaId) {
        return miembroRepo.findByFamilia_Id(familiaId).stream()
                .map(x -> mapper.map(x, MiembroFamiliarDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MiembroFamiliarDTO> obtener(Long miembroId) {
        return miembroRepo.findById(miembroId).map(x -> mapper.map(x, MiembroFamiliarDTO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public MiembroFamiliar getRef(Long miembroId) {
        return miembroRepo.findById(miembroId)
                .orElseThrow(() -> new NoSuchElementException("Miembro no existe: " + miembroId));
    }

    @Override
    public void validarMiembroEnFamilia(Long familiaId, Long miembroId) {
        var mf = getRef(miembroId);
        if (!mf.getFamilia().getId().equals(familiaId)) {
            throw new IllegalArgumentException("El miembro no pertenece a la familia indicada");
        }
    }
}
