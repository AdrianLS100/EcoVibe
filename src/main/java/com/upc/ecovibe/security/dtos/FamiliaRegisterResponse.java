package com.upc.ecovibe.security.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class FamiliaRegisterResponse {
    private Long userId;
    private Long familiaId;
    private Integer numeroMiembros;
    private boolean emailVerificado;
    private String verificationUrl;
}
