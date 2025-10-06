package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.RecursoDTO;
import com.upc.ecovibe.entities.RecursoEducativo.Tipo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRecursoEducativoService {
    Page<RecursoDTO> listar(Tipo tipo, String q, int page, int size);
    RecursoDTO obtener(Long id);
    List<RecursoDTO> relacionados(Long id, int limit);
}
