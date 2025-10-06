package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class FactoresEmisionDTO {
    private Long id;
    private String categoria;
    private String subcategoria;
    private String unidadBase;
    private BigDecimal factorKgco2ePerUnidad;
    private String fuente;
    private Boolean vigente;
    private Instant creadoEn;
}
