package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.FamiliaDTO;
import com.upc.ecovibe.interfaces.IFamiliaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class FamiliaController {

    private final IFamiliaService familiaService;

    public FamiliaController(IFamiliaService s) { this.familiaService = s; }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/familia")
    public ResponseEntity<FamiliaDTO> crear(@RequestBody @Valid FamiliaDTO body) {
        FamiliaDTO creada = familiaService.crear(body.getOwnerUserId(), body.getNombre());
        return ResponseEntity.ok(creada);
    }

    @PreAuthorize("hasAnyRole('ADMIN','FAMILIAR')")
    @PostMapping("/familias/by-owner")
    public ResponseEntity<List<FamiliaDTO>> listar(@RequestBody Map<String, Object> body) {
        Object ownerRaw = body.get("ownerUserId");
        if (ownerRaw == null) {
            return ResponseEntity.badRequest().build();
        }
        Long ownerUserId = (ownerRaw instanceof Number) ? ((Number) ownerRaw).longValue() : Long.valueOf(String.valueOf(ownerRaw));
        return ResponseEntity.ok(familiaService.listarPorOwner(ownerUserId));
    }
}
