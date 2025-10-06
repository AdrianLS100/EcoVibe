package com.upc.ecovibe.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CalculadoraInstitucionDTO {

    @NotNull
    private LocalDate fecha;

    private BigDecimal kwhMes;
    private BigDecimal glpKg;

    private BigDecimal kmBusEscolar;
    private BigDecimal kmVanEscolar;
    private BigDecimal kmAutoDocentes;

    private BigDecimal residuosKg;

    private BigDecimal energiaKgCO2e;
    private BigDecimal transporteKgCO2e;
    private BigDecimal residuosKgCO2e;
    private BigDecimal totalKgCO2e;
}
