package com.upc.ecovibe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class RankingDTO {
    private Long usuarioId;
    private String username;
    private String distrito;
    private BigDecimal totalKgCO2e;
    private int puesto;
}
