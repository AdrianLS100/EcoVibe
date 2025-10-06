package com.upc.ecovibe.dtos;

import lombok.Data;

@Data
public class RecursoDTO {
    private Long id;
    private String titulo;
    private String tipo;
    private String url;
    private String tema;
}
