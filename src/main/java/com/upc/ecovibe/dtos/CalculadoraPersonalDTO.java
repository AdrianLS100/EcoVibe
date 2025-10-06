package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CalculadoraPersonalDTO {
    private BigDecimal horasBusSemana;
    private BigDecimal horasTrenSemana;
    private BigDecimal horasMetropolitanoSemana;
    private BigDecimal horasAutoSemana;

    private BigDecimal kwhMes;
    private Integer balonesGlp10kgMes;

    private Integer diasCarnePorSemana;

    private Integer bolsas5L;
    private Integer bolsas10L;
    private Integer bolsas20L;
    private List<String> tiposReciclaje;

    private BigDecimal totalKgCO2e;
}
