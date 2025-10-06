package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.ResiduoDTO;
import com.upc.ecovibe.interfaces.IResiduoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class ResiduoController {

    @Autowired
    private IResiduoService residuoService;

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/residuos/by-actividad")
    public ResponseEntity<List<ResiduoDTO>> listarPorActividad(@RequestBody Map<String, Object> body) {
        Long actividadId = Long.valueOf(String.valueOf(body.get("actividadId")));
        List<ResiduoDTO> lista = residuoService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/residuo")
    public ResponseEntity<ResiduoDTO> crear(@Valid @RequestBody ResiduoDTO dto) {
        ResiduoDTO creado = residuoService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasAnyRole('USER','FAMILIAR')")
    @DeleteMapping("/residuo/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        residuoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
