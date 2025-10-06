package com.upc.ecovibe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class RecompensaDTO {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer costoPuntos;
}
