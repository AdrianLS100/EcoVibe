package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.ActividadesDiariasDTO;
import com.upc.ecovibe.interfaces.IActividadesDiariasService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class ActividadesDiariasController {

    @Autowired
    private IActividadesDiariasService actividadesService;

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR','INSTITUCION')")
    @PostMapping("/actividad")
    public ResponseEntity<ActividadesDiariasDTO> crearOObtener(@RequestBody CrearActividadRequest req) {
        var dto = actividadesService.crearOObtener(
                req.usuarioId(), req.fecha(), req.descripcion(), req.familiaId(), req.institucionId()
        );
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR','INSTITUCION')")
    @GetMapping("/actividad/{actividadId}")
    public ResponseEntity<ActividadesDiariasDTO> obtenerPorId(@PathVariable Long actividadId) {
        Optional<ActividadesDiariasDTO> dto = actividadesService.obtenerPorId(actividadId);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR','INSTITUCION')")
    @PostMapping("/actividad/by-fecha")
    public ResponseEntity<ActividadesDiariasDTO> obtenerPorUsuarioYFecha(@RequestBody Map<String, Object> body) {
        Long usuarioId = Long.valueOf(String.valueOf(body.get("usuarioId")));
        LocalDate fecha = LocalDate.parse(String.valueOf(body.get("fecha")));
        Optional<ActividadesDiariasDTO> dto = actividadesService.obtenerPorUsuarioYFecha(usuarioId, fecha);
        return dto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR','INSTITUCION')")
    @PostMapping("/actividades/search")
    public ResponseEntity<List<ActividadesDiariasDTO>> listarPorUsuarioYRango(@RequestBody Map<String, Object> body) {
        Long usuarioId = Long.valueOf(String.valueOf(body.get("usuarioId")));
        LocalDate desde = LocalDate.parse(String.valueOf(body.get("desde")));
        LocalDate hasta = LocalDate.parse(String.valueOf(body.get("hasta")));
        List<ActividadesDiariasDTO> lista = actividadesService.listarPorUsuarioYRango(usuarioId, desde, hasta);
        return ResponseEntity.ok(lista);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR','INSTITUCION')")
    @DeleteMapping("/actividad/{actividadId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long actividadId) {
        actividadesService.eliminar(actividadId);
        return ResponseEntity.noContent().build();
    }

    public record CrearActividadRequest(
            @NotNull Long usuarioId,
            @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            String descripcion,
            Long familiaId,
            Long institucionId
    ) {}
}
