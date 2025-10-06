package com.upc.ecovibe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ActividadesDiariasDTO {
    private Long id;
    private Long usuarioId;
    private Long familiaId;
    private Long miembroId;
    private Long institucionId;
    private java.time.LocalDate fecha;
    private String descripcion;
}
