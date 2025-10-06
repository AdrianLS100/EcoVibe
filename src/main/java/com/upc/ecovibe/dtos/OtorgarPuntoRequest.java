package com.upc.ecovibe.dtos;

import lombok.Data;

@Data
public class OtorgarPuntoRequest {
    private Long usuarioId;
    private String codigoAccion;
    private String detalle;
}
