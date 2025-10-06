package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CalculadoraFamiliarDTO {
    private Integer numeroPersonas;

    private BigDecimal horasBusSemana;
    private BigDecimal horasTrenSemana;
    private BigDecimal horasMetropolitanoSemana;
    private BigDecimal horasAutoSemana;
    private String tipoCombustibleAuto;

    private BigDecimal kwhMes;
    private Integer balonesGlp10kgMes;
    private BigDecimal gastoGasNaturalSolesMes;

    private Integer diasCarnePorSemana;
    private String formaAdquirirAlimentos;

    private Integer bolsas5L;
    private Integer bolsas10L;
    private Integer bolsas20L;
    private List<String> tiposReciclaje;

    private BigDecimal totalKgCO2e;
}
