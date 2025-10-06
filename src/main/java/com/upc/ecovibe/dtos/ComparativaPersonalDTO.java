package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComparativaPersonalDTO {
    private Long usuarioId;
    private LocalDate fecha;

    private BigDecimal miTotalKgCO2e;

    private BigDecimal promedioAmigosKgCO2e;
    private Integer amigosConsiderados;

    private BigDecimal promedioRegionalKgCO2e;
    private Integer usuariosRegionales;
}