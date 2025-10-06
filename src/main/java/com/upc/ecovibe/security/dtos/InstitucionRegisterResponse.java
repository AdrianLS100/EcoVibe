package com.upc.ecovibe.security.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class InstitucionRegisterResponse {
    private Long userId;
    private Long institucionId;
    private String username;
    private String email;
    private boolean emailVerificado;
    private String verificationUrl;
}
