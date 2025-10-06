package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.EnergiaDTO;
import com.upc.ecovibe.interfaces.IEnergiaService;
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
public class EnergiaController {

    @Autowired
    private IEnergiaService energiaService;

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/energias/by-actividad")
    public ResponseEntity<List<EnergiaDTO>> listarPorActividad(@RequestBody Map<String, Object> body) {
        Long actividadId = Long.valueOf(String.valueOf(body.get("actividadId")));
        List<EnergiaDTO> lista = energiaService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/energia")
    public ResponseEntity<EnergiaDTO> crear(@Valid @RequestBody EnergiaDTO dto) {
        EnergiaDTO creado = energiaService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @DeleteMapping("/energia/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        energiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
