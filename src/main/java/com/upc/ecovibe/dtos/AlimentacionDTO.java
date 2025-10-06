package com.upc.ecovibe.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AlimentacionDTO {
    private Long id;
    private Long actividadId;
    private String tipoDieta;
    private String frecuencia;
    private LocalDate creadoEn;
    private Long miembroId;
}
