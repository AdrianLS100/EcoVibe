package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.TransporteDTO;
import com.upc.ecovibe.interfaces.ITransporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class TransporteController {

    @Autowired
    private ITransporteService transporteService;

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/transportes/by-actividad")
    public ResponseEntity<List<TransporteDTO>> listarPorActividad(@RequestBody Map<String, Object> body) {
        Long actividadId = Long.valueOf(String.valueOf(body.get("actividadId")));
        List<TransporteDTO> lista = transporteService.listarPorActividad(actividadId);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/transporte")
    public ResponseEntity<TransporteDTO> crear(@Valid @RequestBody TransporteDTO dto) {
        TransporteDTO creado = transporteService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @DeleteMapping("/transporte/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        transporteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
