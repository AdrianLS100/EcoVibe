package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.ActividadesDiariasDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.interfaces.IActividadesDiariasService;
import com.upc.ecovibe.interfaces.IFamiliaService;
import com.upc.ecovibe.repositories.ActividadesDiariasRepository;
import com.upc.ecovibe.repositories.InstitucionEducativaRepository;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ActividadesDiariasService implements IActividadesDiariasService {
    @Autowired
    private ActividadesDiariasRepository actividadesRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private IFamiliaService familiaService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InstitucionEducativaRepository instRepo;


    @Override
    public ActividadesDiariasDTO crearOObtener(Long usuarioId, LocalDate fecha, String descripcion,
                                               Long familiaId, Long institucionId) {

        if (familiaId != null && institucionId != null) {
            throw new IllegalArgumentException("Una actividad no puede ser familiar e institucional a la vez.");
        }

        var actividad = actividadesRepo
                .findByUsuario_IdAndFecha(usuarioId, fecha)
                .orElseGet(() -> {
                    var a = new ActividadesDiarias();
                    var u = userRepo.findById(usuarioId)
                            .orElseThrow(() -> new RuntimeException("Usuario no existe: " + usuarioId));
                    a.setUsuario(u);
                    a.setFecha(fecha);
                    return a;
                });

        actividad.setDescripcion(descripcion);

        if (institucionId != null) {
            var inst = instRepo.findById(institucionId)
                    .orElseThrow(() -> new RuntimeException("Institución no existe: " + institucionId));
            actividad.setInstitucion(inst);
            actividad.setFamilia(null);
        } else if (familiaId != null) {
            actividad.setFamilia(familiaService.getRef(familiaId));
            actividad.setInstitucion(null);
        } else {
            actividad.setFamilia(null);
            actividad.setInstitucion(null);
        }

        actividad = actividadesRepo.save(actividad);

        var dto = modelMapper.map(actividad, ActividadesDiariasDTO.class);
        dto.setUsuarioId(actividad.getUsuario().getId());
        dto.setFamiliaId(actividad.getFamilia() != null ? actividad.getFamilia().getId() : null);
        dto.setInstitucionId(actividad.getInstitucion() != null ? actividad.getInstitucion().getId() : null);
        return dto;
    }

    @Override
    public Optional<ActividadesDiariasDTO> obtenerPorId(Long actividadId) {
        return actividadesRepo.findById(actividadId)
                .map(entity -> modelMapper.map(entity, ActividadesDiariasDTO.class));
    }

    @Override
    public Optional<ActividadesDiariasDTO> obtenerPorUsuarioYFecha(Long usuarioId, LocalDate fecha) {
        return actividadesRepo.findByUsuario_IdAndFecha(usuarioId, fecha)
                .map(entity -> modelMapper.map(entity, ActividadesDiariasDTO.class));
    }

    @Override
    public List<ActividadesDiariasDTO> listarPorUsuarioYRango(Long usuarioId, LocalDate desde, LocalDate hasta) {
        return actividadesRepo.findByUsuario_IdAndFechaBetween(usuarioId, desde, hasta)
                .stream()
                .map(entity -> modelMapper.map(entity, ActividadesDiariasDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long actividadId) {
        actividadesRepo.deleteById(actividadId);
    }
}
