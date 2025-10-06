package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.ComparativaPersonalDTO;
import com.upc.ecovibe.dtos.ComparativaPersonalRequest;
import com.upc.ecovibe.interfaces.IComparativaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class ComparativaController {


    private final IComparativaService service;

    public ComparativaController(IComparativaService service) {
        this.service = service;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR')")
    @PostMapping("/comparativas/personal")
    public ResponseEntity<ComparativaPersonalDTO> compararPersonal(@Valid @RequestBody ComparativaPersonalRequest req) {
        var dto = service.compararPersonalDia(
                req.getUsuarioId(),
                req.getFecha(),
                req.getAmigosIds(),
                req.getDistrito()
        );
        return ResponseEntity.ok(dto);
    }
}
