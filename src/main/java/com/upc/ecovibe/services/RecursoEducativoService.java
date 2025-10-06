package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.RecursoDTO;
import com.upc.ecovibe.entities.RecursoEducativo;
import com.upc.ecovibe.entities.RecursoEducativo.Tipo;
import com.upc.ecovibe.interfaces.IRecursoEducativoService;
import com.upc.ecovibe.repositories.RecursoEducativoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecursoEducativoService implements IRecursoEducativoService {

    private final RecursoEducativoRepository repo;
    private final ModelMapper mapper;

    public RecursoEducativoService(RecursoEducativoRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public Page<RecursoDTO> listar(Tipo tipo, String q, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creadoEn"));
        String pattern = (q == null || q.isBlank()) ? null : "%" + q.toLowerCase() + "%";
        return repo.search(tipo, pattern, pageable)
                .map(r -> mapper.map(r, RecursoDTO.class));
    }

    @Override
    public RecursoDTO obtener(Long id) {
        var r = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recurso no existe: " + id));
        return mapper.map(r, RecursoDTO.class);
    }

    @Override
    public List<RecursoDTO> relacionados(Long id, int limit) {
        var base = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Recurso no existe: " + id));

        var page = repo.relacionados(
                base.getId(),
                base.getTema(),
                base.getTipo(),
                base.getFuente(),
                PageRequest.of(0, limit)
        );

        return page.map(r -> mapper.map(r, RecursoDTO.class))
                .getContent();
    }

    private static String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    public List<RecursoDTO> listarSoloLista(Tipo tipo, String q, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "creadoEn"));
        return repo.search(tipo, (q == null || q.isBlank()) ? null : q, pageable)
                .map(r -> mapper.map(r, RecursoDTO.class))
                .getContent();
    }
}
