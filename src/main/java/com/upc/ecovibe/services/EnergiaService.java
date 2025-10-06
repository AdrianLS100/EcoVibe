package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.EnergiaDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.entities.Energia;
import com.upc.ecovibe.interfaces.IEnergiaService;
import com.upc.ecovibe.repositories.ActividadesDiariasRepository;
import com.upc.ecovibe.repositories.EnergiaRepository;
import com.upc.ecovibe.repositories.MiembroFamiliarRepository;
import com.upc.ecovibe.security.validators.RolMiembroValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EnergiaService implements IEnergiaService {

    @Autowired private EnergiaRepository energiaRepo;
    @Autowired private ActividadesDiariasRepository actividadesRepo;
    @Autowired private MiembroFamiliarRepository miembroRepo;
    @Autowired private RolMiembroValidator rolMiembroValidator;
    @Autowired private ModelMapper modelMapper;

    @Override
    public EnergiaDTO crear(EnergiaDTO dto) {
        // Regla PERSONAL/FAMILIAR
        rolMiembroValidator.validateMiembroRequiredOrForbidden(dto.getMiembroId());

        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad con ID " + dto.getActividadId() + " no encontrada"));

        Energia entity = modelMapper.map(dto, Energia.class);
        entity.setActividad(actividad);

        if (dto.getMiembroId() != null) {
            var miembro = miembroRepo.findById(dto.getMiembroId())
                    .orElseThrow(() -> new NoSuchElementException("Miembro con ID " + dto.getMiembroId() + " no encontrado"));
            entity.setMiembro(miembro);
        } else {
            entity.setMiembro(null);
        }

        Energia saved = energiaRepo.save(entity);

        EnergiaDTO out = modelMapper.map(saved, EnergiaDTO.class);
        out.setActividadId(saved.getActividad().getId());
        out.setMiembroId(saved.getMiembro() != null ? saved.getMiembro().getId() : null);
        return out;
    }

    @Override
    public List<EnergiaDTO> listarPorActividad(Long actividadId) {
        return energiaRepo.findByActividad_Id(actividadId).stream()
                .map(e -> {
                    EnergiaDTO d = modelMapper.map(e, EnergiaDTO.class);
                    d.setActividadId(e.getActividad().getId());
                    d.setMiembroId(e.getMiembro() != null ? e.getMiembro().getId() : null);
                    return d;
                })
                .toList();
    }

    @Override
    public BigDecimal sumarConsumo(Long actividadId) {
        BigDecimal suma = energiaRepo.sumConsumoByActividadId(actividadId);
        return (suma != null) ? suma : BigDecimal.ZERO;
    }

    @Override
    public void eliminar(Long energiaId) {
        if (!energiaRepo.existsById(energiaId)) {
            throw new NoSuchElementException("Energía con ID " + energiaId + " no encontrada");
        }
        energiaRepo.deleteById(energiaId);
    }

    @Override
    public long eliminarPorActividad(Long actividadId) {
        return energiaRepo.deleteByActividad_Id(actividadId);
    }
}
