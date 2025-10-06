package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.AlimentacionDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.entities.Alimentacion;
import com.upc.ecovibe.interfaces.IAlimentacionService;
import com.upc.ecovibe.repositories.ActividadesDiariasRepository;
import com.upc.ecovibe.repositories.AlimentacionRepository;
import com.upc.ecovibe.repositories.MiembroFamiliarRepository;
import com.upc.ecovibe.security.validators.RolMiembroValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AlimentacionService implements IAlimentacionService {
    @Autowired private AlimentacionRepository alimentacionRepo;
    @Autowired private ActividadesDiariasRepository actividadesRepo;
    @Autowired private MiembroFamiliarRepository miembroRepo;
    @Autowired private RolMiembroValidator rolMiembroValidator;
    @Autowired private ModelMapper modelMapper;

    @Override
    public AlimentacionDTO crear(AlimentacionDTO dto) {
        // Regla PERSONAL/FAMILIAR
        rolMiembroValidator.validateMiembroRequiredOrForbidden(dto.getMiembroId());

        ActividadesDiarias actividad = actividadesRepo.findById(dto.getActividadId())
                .orElseThrow(() -> new NoSuchElementException("Actividad con ID " + dto.getActividadId() + " no encontrada"));

        Alimentacion entity = modelMapper.map(dto, Alimentacion.class);
        entity.setActividad(actividad);

        if (dto.getMiembroId() != null) {
            var miembro = miembroRepo.findById(dto.getMiembroId())
                    .orElseThrow(() -> new NoSuchElementException("Miembro con ID " + dto.getMiembroId() + " no encontrado"));
            entity.setMiembro(miembro);
        } else {
            entity.setMiembro(null);
        }

        Alimentacion saved = alimentacionRepo.save(entity);

        AlimentacionDTO out = modelMapper.map(saved, AlimentacionDTO.class);
        out.setActividadId(saved.getActividad().getId());
        out.setMiembroId(saved.getMiembro() != null ? saved.getMiembro().getId() : null);
        return out;
    }

    @Override
    public List<AlimentacionDTO> listarPorActividad(Long actividadId) {
        return alimentacionRepo.findByActividad_Id(actividadId)
                .stream()
                .map(e -> {
                    AlimentacionDTO d = modelMapper.map(e, AlimentacionDTO.class);
                    d.setActividadId(e.getActividad().getId());
                    d.setMiembroId(e.getMiembro() != null ? e.getMiembro().getId() : null);
                    return d;
                })
                .toList();
    }

    @Override
    public void eliminar(Long alimentacionId) {
        if (!alimentacionRepo.existsById(alimentacionId)) {
            throw new NoSuchElementException("Alimentación con ID " + alimentacionId + " no encontrada");
        }
        alimentacionRepo.deleteById(alimentacionId);
    }
}
