package com.upc.ecovibe.security.dtos;

import jakarta.validation.constraints.*;

@lombok.Data
public class InstitucionRegisterRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    @Email @NotBlank private String email;
    @NotBlank private String representanteNombre;
    @NotBlank private String distrito;

    // datos de la institución
    @NotBlank private String nombreInstitucion;
    @NotBlank private String campus;
    @NotNull  private Integer numeroEstudiantes;
}
