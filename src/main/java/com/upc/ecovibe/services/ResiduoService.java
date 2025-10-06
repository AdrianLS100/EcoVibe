package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.ResiduoDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.entities.Residuo;
import com.upc.ecovibe.interfaces.IResiduoService;
import com.upc.ecovibe.repositories.ActividadesDiariasRepository;
import com.upc.ecovibe.repositories.MiembroFamiliarRepository;
import com.upc.ecovibe.repositories.ResiduoRepository;
import com.upc.ecovibe.security.validators.RolMiembroValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResiduoService implements IResiduoService {

    @Autowired private ResiduoRepository residuoRepo;
    @Autowired private ActividadesDiariasRepository actividadesRepo;
    @Autowired private MiembroFamiliarRepository miembroRepo;
    @Autowired private RolMiembroValidator rolMiembroValidator;
    @Autowired private ModelMapper modelMapper;

    @Override
    public ResiduoDTO crear(ResiduoDTO dto) {
        rolMiembroValidator.validateMiembroRequiredOrForbidden(dto.getMiembroId());

        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad con ID " + dto.getActividadId() + " no encontrada"));

        Residuo entity = modelMapper.map(dto, Residuo.class);
        entity.setActividad(actividad);

        if (dto.getMiembroId() != null) {
            var miembro = miembroRepo.findById(dto.getMiembroId())
                    .orElseThrow(() -> new NoSuchElementException("Miembro con ID " + dto.getMiembroId() + " no encontrado"));
            entity.setMiembro(miembro);
        } else {
            entity.setMiembro(null);
        }

        Residuo saved = residuoRepo.save(entity);

        ResiduoDTO out = modelMapper.map(saved, ResiduoDTO.class);
        out.setActividadId(saved.getActividad().getId());
        out.setMiembroId(saved.getMiembro() != null ? saved.getMiembro().getId() : null);
        return out;
    }

    @Override
    public List<ResiduoDTO> listarPorActividad(Long actividadId) {
        return residuoRepo.findByActividad_Id(actividadId).stream()
                .map(e -> {
                    ResiduoDTO d = modelMapper.map(e, ResiduoDTO.class);
                    d.setActividadId(e.getActividad().getId());
                    d.setMiembroId(e.getMiembro() != null ? e.getMiembro().getId() : null);
                    return d;
                })
                .toList();
    }

    @Override
    public void eliminar(Long residuoId) {
        if (!residuoRepo.existsById(residuoId)) {
            throw new NoSuchElementException("Residuo con ID " + residuoId + " no encontrado");
        }
        residuoRepo.deleteById(residuoId);
    }
}
