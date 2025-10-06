package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SeriePuntoDTO {
    private LocalDate fecha;
    private BigDecimal totalKgCO2e;
}
