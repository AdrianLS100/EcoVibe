package com.upc.ecovibe.controllers;

import com.upc.ecovibe.entities.InstitucionEducativa;
import com.upc.ecovibe.repositories.InstitucionEducativaRepository;
import com.upc.ecovibe.security.dtos.InstitucionRegisterRequest;
import com.upc.ecovibe.security.dtos.InstitucionRegisterResponse;
import com.upc.ecovibe.services.InstitucionEducativaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")

public class InstitucionEducativaController {

    @Autowired
    private InstitucionEducativaService service;

    @Autowired
    private InstitucionEducativaRepository repo;

    public InstitucionEducativaController(InstitucionEducativaService s) { this.service = s; }

    @PostMapping("/institucion")
    public ResponseEntity<InstitucionRegisterResponse> registrar(@Valid @RequestBody InstitucionRegisterRequest req) {
        return ResponseEntity.ok(service.registrar(req));
    }

    @PreAuthorize("hasAnyRole('ADMIN','INSTITUCION')")
    @PostMapping("/by-owner")
    public ResponseEntity<List<InstitucionEducativa>> listarPorOwner(@RequestBody Long ownerUserId) {
        return ResponseEntity.ok(repo.findByOwner_Id(ownerUserId));
    }
}
