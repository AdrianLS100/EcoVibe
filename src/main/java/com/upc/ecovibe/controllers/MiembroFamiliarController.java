package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.MiembroFamiliarDTO;
import com.upc.ecovibe.interfaces.IMiembroFamiliarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class MiembroFamiliarController {

    private final IMiembroFamiliarService servicio;

    public MiembroFamiliarController(IMiembroFamiliarService s) { this.servicio = s; }

    @PreAuthorize("hasAnyRole('ADMIN','FAMILIAR')")
    @PostMapping("/miembro-familiar")
    public ResponseEntity<MiembroFamiliarDTO> crear(@RequestBody MiembroFamiliarDTO req) {
        var dto = servicio.crear(req.getFamiliaId(), req.getNombre(), req.getRelacion(), req.getUserId());
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FAMILIAR')")
    @PostMapping("/miembros-familiar/by-familia")
    public ResponseEntity<List<MiembroFamiliarDTO>> listar(@RequestBody Map<String, Object> body) {
        Object famRaw = body.get("familiaId");
        if (famRaw == null) return ResponseEntity.badRequest().build();
        Long familiaId = (famRaw instanceof Number) ? ((Number) famRaw).longValue() : Long.valueOf(String.valueOf(famRaw));
        return ResponseEntity.ok(servicio.listar(familiaId));
    }
}
