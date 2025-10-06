package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReporteFamiliaDTO {
    private Long familiaId;
    private LocalDate fecha;

    private BigDecimal transporteKgCO2e;
    private BigDecimal energiaKgCO2e;
    private BigDecimal alimentacionKgCO2e;
    private BigDecimal residuosKgCO2e;

    private BigDecimal totalKgCO2e;
}
