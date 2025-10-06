package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.FactoresEmisionDTO;
import com.upc.ecovibe.interfaces.IFactoresEmisionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class FactoresEmisionController {

    @Autowired
    private IFactoresEmisionService factoresService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/factor")
    public ResponseEntity<FactoresEmisionDTO> crear(@Valid @RequestBody FactoresEmisionDTO dto) {
        FactoresEmisionDTO creado = factoresService.crear(dto);
        return ResponseEntity.ok(creado);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/factor/{id}")
    public ResponseEntity<FactoresEmisionDTO> obtener(@PathVariable Long id) {
        Optional<FactoresEmisionDTO> dto = factoresService.obtener(id);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping("/factor/{id}")
    public ResponseEntity<FactoresEmisionDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody FactoresEmisionDTO dto) {
        FactoresEmisionDTO actualizado = factoresService.actualizar(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/factor/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        factoresService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/factor/vigente")
    public ResponseEntity<FactoresEmisionDTO> buscarVigente(@RequestBody Map<String, Object> body) {
        String categoria = String.valueOf(body.get("categoria"));
        String unidadBase = String.valueOf(body.get("unidadBase"));
        String subcategoria = body.get("subcategoria") == null ? null : String.valueOf(body.get("subcategoria"));

        return factoresService.buscarVigente(categoria, subcategoria, unidadBase)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/factores/vigentes/by-categoria")
    public ResponseEntity<List<FactoresEmisionDTO>> listarVigentesPorCategoria(@RequestBody Map<String, Object> body) {
        String categoria = String.valueOf(body.get("categoria"));
        List<FactoresEmisionDTO> lista = factoresService.listarVigentesPorCategoria(categoria);
        return ResponseEntity.ok(lista);
    }
}
