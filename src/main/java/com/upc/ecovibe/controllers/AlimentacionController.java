package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.AlimentacionDTO;
import com.upc.ecovibe.interfaces.IAlimentacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class AlimentacionController {

    @Autowired
    private IAlimentacionService alimentacionService;

    // Antes GET con param → ahora POST con body
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/alimentaciones/by-actividad")
    public ResponseEntity<List<AlimentacionDTO>> listarPorActividad(@RequestBody Map<String, Object> body) {
        Long actividadId = Long.valueOf(String.valueOf(body.get("actividadId")));
        List<AlimentacionDTO> lista = alimentacionService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/alimentacion")
    public ResponseEntity<AlimentacionDTO> crear(@Valid @RequestBody AlimentacionDTO dto) {
        AlimentacionDTO creado = alimentacionService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @DeleteMapping("/alimentacion/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alimentacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/alimentaciones/delete-by-actividad")
    public ResponseEntity<Long> eliminarPorActividad(@RequestBody Map<String, Object> body) {
        Long actividadId = Long.valueOf(String.valueOf(body.get("actividadId")));
        long eliminados = alimentacionService.eliminarPorActividad(actividadId);
        return ResponseEntity.ok(eliminados);
    }
}
