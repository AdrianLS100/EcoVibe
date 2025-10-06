package com.upc.ecovibe.dtos;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EnergiaDTO {
    private Long id;
    private Long actividadId;
    private String tipo;
    private BigDecimal consumo;
    private String unidad;
    private String frecuencia;
    private LocalDate creadoEn;
    private Long miembroId;

}
