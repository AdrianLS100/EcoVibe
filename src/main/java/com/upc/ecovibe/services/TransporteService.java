package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.TransporteDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.entities.Transporte;
import com.upc.ecovibe.interfaces.ITransporteService;
import com.upc.ecovibe.repositories.ActividadesDiariasRepository;
import com.upc.ecovibe.repositories.MiembroFamiliarRepository;
import com.upc.ecovibe.repositories.TransporteRepository;
import com.upc.ecovibe.security.validators.RolMiembroValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TransporteService implements ITransporteService {
    @Autowired private TransporteRepository transporteRepo;
    @Autowired private ActividadesDiariasRepository actividadesRepo;
    @Autowired private MiembroFamiliarRepository miembroRepo;
    @Autowired private RolMiembroValidator rolMiembroValidator;
    @Autowired private ModelMapper modelMapper;

    @Override
    public TransporteDTO crear(TransporteDTO dto) {
        rolMiembroValidator.validateMiembroRequiredOrForbidden(dto.getMiembroId());

        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad con ID " + dto.getActividadId() + " no encontrada"));

        Transporte entity = modelMapper.map(dto, Transporte.class);
        entity.setActividad(actividad);

        if (dto.getMiembroId() != null) {
            var miembro = miembroRepo.findById(dto.getMiembroId())
                    .orElseThrow(() -> new NoSuchElementException("Miembro con ID " + dto.getMiembroId() + " no encontrado"));
            entity.setMiembro(miembro);
        } else {
            entity.setMiembro(null);
        }

        Transporte saved = transporteRepo.save(entity);

        TransporteDTO out = modelMapper.map(saved, TransporteDTO.class);
        out.setActividadId(saved.getActividad().getId());
        out.setMiembroId(saved.getMiembro() != null ? saved.getMiembro().getId() : null);
        return out;
    }

    @Override
    public List<TransporteDTO> listarPorActividad(Long actividadId) {
        return transporteRepo.findByActividad_Id(actividadId).stream()
                .map(e -> {
                    TransporteDTO d = modelMapper.map(e, TransporteDTO.class);
                    d.setActividadId(e.getActividad().getId());
                    d.setMiembroId(e.getMiembro() != null ? e.getMiembro().getId() : null);
                    return d;
                })
                .toList();
    }

    @Override
    public BigDecimal sumarDistanciaKm(Long actividadId) {
        BigDecimal suma = transporteRepo.sumDistanciaKmByActividadId(actividadId);
        return (suma != null) ? suma : BigDecimal.ZERO;
    }

    @Override
    public void eliminar(Long transporteId) {
        if (!transporteRepo.existsById(transporteId)) {
            throw new NoSuchElementException("Transporte con ID " + transporteId + " no encontrado");
        }
        transporteRepo.deleteById(transporteId);
    }

    @Override
    public long eliminarPorActividad(Long actividadId) {
        return transporteRepo.deleteByActividad_Id(actividadId);
    }
}
