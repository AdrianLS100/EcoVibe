package com.upc.ecovibe.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MiembroFamiliarDTO {
    private Long id;
    private Long familiaId;
    private String nombre;
    private String relacion;
    private Long userId;
}
