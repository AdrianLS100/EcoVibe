package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.ReporteDTO;
import com.upc.ecovibe.dtos.ReporteFamiliaDTO;
import com.upc.ecovibe.dtos.ReporteInstitucionDTO;
import com.upc.ecovibe.interfaces.IReporteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
@RequestMapping("/api")
public class ReporteController {

    @Autowired
    private IReporteService reporteService;

    @PreAuthorize("hasAnyRole('ADMIN','USER','FAMILIAR')")
    @PostMapping("/reportes/dia")
    public ResponseEntity<ReporteDTO> reporteDiaBody(@RequestBody @Valid Req req) {
        return ResponseEntity.ok(reporteService.reporteDia(req.usuarioId(), req.fecha()));
    }
    record Req(@NotNull Long usuarioId,
               @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {}


    @PreAuthorize("hasAnyRole('ADMIN','FAMILIAR')")
    @PostMapping("/reportes/familia/dia")
    public ResponseEntity<ReporteFamiliaDTO> reporteFamiliaDia(@RequestBody @Valid FamiliaReq req) {
        return ResponseEntity.ok(reporteService.reporteFamiliaDia(req.familiaId(), req.fecha()));
    }
    record FamiliaReq(@NotNull Long familiaId,
                      @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {}

    @PreAuthorize("hasAnyRole('ADMIN','INSTITUCION')")
    @PostMapping("/reportes/institucion/dia")
    public ResponseEntity<ReporteInstitucionDTO> reporteInstitucionDia(@RequestBody @Valid InstReq req) {
        return ResponseEntity.ok(reporteService.reporteInstitucionDia(req.institucionId(), req.fecha()));
    }
    record InstReq(@NotNull Long institucionId,
                   @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {}

}
