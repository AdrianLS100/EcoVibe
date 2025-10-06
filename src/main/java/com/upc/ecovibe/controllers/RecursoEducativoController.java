package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.RecursoDTO;
import com.upc.ecovibe.entities.RecursoEducativo.Tipo;
import com.upc.ecovibe.services.RecursoEducativoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class RecursoEducativoController {

    private final RecursoEducativoService service;

    public RecursoEducativoController(RecursoEducativoService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @GetMapping("/recursos")
    public ResponseEntity<List<RecursoDTO>> listar(
            @RequestParam(required = false) Tipo tipo,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        return ResponseEntity.ok(service.listarSoloLista(tipo, q, page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @GetMapping("/recursos/{id}")
    public ResponseEntity<RecursoDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @GetMapping("/recursos/{id}/relacionados")
    public ResponseEntity<List<RecursoDTO>> relacionados(
            @PathVariable Long id,
            @RequestParam(defaultValue = "6") int limit
    ) {
        return ResponseEntity.ok(service.relacionados(id, limit));
    }
}
