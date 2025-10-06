package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.FactoresEmisionDTO;
import com.upc.ecovibe.entities.FactoresEmision;
import com.upc.ecovibe.interfaces.IFactoresEmisionService;
import com.upc.ecovibe.repositories.FactoresEmisionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FactoresEmisionService implements IFactoresEmisionService {

    @Autowired
    private FactoresEmisionRepository factoresRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FactoresEmisionDTO crear(FactoresEmisionDTO dto) {
        FactoresEmision entity = modelMapper.map(dto, FactoresEmision.class);
        FactoresEmision saved = factoresRepo.save(entity);
        return modelMapper.map(saved, FactoresEmisionDTO.class);
    }

    @Override
    public FactoresEmisionDTO actualizar(Long id, FactoresEmisionDTO dto) {
        return factoresRepo.findById(id)
                .map(existing -> {
                    FactoresEmision entity = modelMapper.map(dto, FactoresEmision.class);
                    entity.setId(id); // aseguramos que se actualice el correcto
                    return modelMapper.map(factoresRepo.save(entity), FactoresEmisionDTO.class);
                })
                .orElseThrow(() -> new NoSuchElementException("Factor de Emisión con ID " + id + " no encontrado"));
    }

    @Override
    public Optional<FactoresEmisionDTO> obtener(Long id) {
        return factoresRepo.findById(id)
                .map(entity -> modelMapper.map(entity, FactoresEmisionDTO.class));
    }

    @Override
    public Optional<FactoresEmisionDTO> buscarVigente(String categoria, String subcategoria, String unidadBase) {
        return factoresRepo.findFirstByCategoriaIgnoreCaseAndSubcategoriaIgnoreCaseAndUnidadBaseIgnoreCaseAndVigenteTrue(
                        categoria, subcategoria, unidadBase)
                .map(entity -> modelMapper.map(entity, FactoresEmisionDTO.class));
    }

    @Override
    public List<FactoresEmisionDTO> listarVigentesPorCategoria(String categoria) {
        return factoresRepo.findByCategoriaIgnoreCaseAndVigenteTrue(categoria).stream()
                .map(entity -> modelMapper.map(entity, FactoresEmisionDTO.class))
                .toList();
    }

    @Override
    public void eliminar(Long id) {
        if (!factoresRepo.existsById(id)) {
            throw new NoSuchElementException("Factor de Emisión con ID " + id + " no encontrado");
        }
        factoresRepo.deleteById(id);
    }
}
