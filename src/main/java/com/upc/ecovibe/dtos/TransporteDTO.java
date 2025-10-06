package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TransporteDTO {
    private Long id;
    private Long actividadId;
    private String medio;
    private BigDecimal distanciaKm;
    private String frecuencia;
    private LocalDate creadoEn;
    private Long miembroId;
}
