package com.upc.ecovibe.security.controllers;

import com.upc.ecovibe.security.dtos.PasswordForgotRequest;
import com.upc.ecovibe.security.dtos.PasswordResetRequest;
import com.upc.ecovibe.security.services.PasswordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class PasswordController {

    private final PasswordService service;

    public PasswordController(PasswordService service) {
        this.service = service;
    }

    @PostMapping("/forgot")
    public ResponseEntity<Map<String, String>> forgot(@Valid @RequestBody PasswordForgotRequest req) {
        service.requestReset(req.getEmail());            // <-- usar getter
        return ResponseEntity.ok(Map.of("message", "Si el email existe, enviamos el enlace de recuperación."));
    }

    @PostMapping("/reset")
    public ResponseEntity<Map<String, String>> reset(@Valid @RequestBody PasswordResetRequest req) {
        service.resetPassword(req.getToken(), req.getNewPassword());  // <-- usar getters
        return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente."));
    }
}
