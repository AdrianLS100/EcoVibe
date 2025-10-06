package com.upc.ecovibe.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class EstadoGamificacionDTO {
    private int puntosTotales;
    private String logroActual;
    private String siguienteLogro;
    private Integer umbralSiguiente;
    private int progresoPorcentaje;
    private String mensaje;
}
