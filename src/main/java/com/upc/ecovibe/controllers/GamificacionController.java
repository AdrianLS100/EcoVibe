package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.*;
import com.upc.ecovibe.services.GamificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gamificacion")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class GamificacionController {

    private final GamificacionService service;

    public GamificacionController(GamificacionService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @PostMapping("/otorgar")
    public ResponseEntity<EstadoGamificacionDTO> otorgar(@RequestBody OtorgarPuntoRequest req) {
        return ResponseEntity.ok(service.otorgarPuntos(req));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @GetMapping("/estado/{usuarioId}")
    public ResponseEntity<EstadoGamificacionDTO> estado(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(service.estado(usuarioId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @GetMapping("/recompensas")
    public ResponseEntity<List<RecompensaDTO>> recompensas() {
        return ResponseEntity.ok(service.listarRecompensas());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR','INSTITUCION')")
    @PostMapping("/canjear")
    public ResponseEntity<EstadoGamificacionDTO> canjear(@RequestBody CanjearRequest req) {
        return ResponseEntity.ok(service.canjear(req));
    }
}
