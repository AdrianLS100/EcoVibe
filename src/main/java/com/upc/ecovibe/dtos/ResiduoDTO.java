package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ResiduoDTO {
    private Long id;
    private Long actividadId;
    private String tipo;
    private BigDecimal pesoKg;
    private Boolean reciclaje;
    private LocalDate creadoEn;
    private Long miembroId;

}
