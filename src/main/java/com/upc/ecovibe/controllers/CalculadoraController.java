package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.CalculadoraInstitucionDTO;
import com.upc.ecovibe.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibe.dtos.CalculadoraFamiliarDTO;
import com.upc.ecovibe.interfaces.ICalculadoraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class CalculadoraController {

    @Autowired
    private ICalculadoraService calculadoraService;
    // PERSONAL

    @PreAuthorize("hasRole('ADMIN') or hasRole('PERSONAL')")
    @PostMapping("/calculadora/personal/estimar")
    public ResponseEntity<CalculadoraPersonalDTO> calcularPersonal(
            @Valid @RequestBody CalculadoraPersonalDTO request) {
        CalculadoraPersonalDTO resp = calculadoraService.calcularp(request);
        return ResponseEntity.ok(resp);
    }

    // =========================
    // FAMILIAR
    // =========================

    @PreAuthorize("hasRole('ADMIN') or hasRole('FAMILIAR')")
    @PostMapping("/calculadora/familiar/estimar")
    public ResponseEntity<CalculadoraFamiliarDTO> calcularFamiliar(
            @Valid @RequestBody CalculadoraFamiliarDTO request) {
        CalculadoraFamiliarDTO resp = calculadoraService.calcularf(request);
        return ResponseEntity.ok(resp);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTITUCION')")
    @PostMapping("/calculadora/institucion/estimar")
    public ResponseEntity<CalculadoraInstitucionDTO> calcularInstitucion(
            @Valid @RequestBody CalculadoraInstitucionDTO request) {
        var resp = calculadoraService.calculari(request);
        return ResponseEntity.ok(resp);
    }
}
