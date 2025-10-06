package com.upc.ecovibe.security.dtos;

import jakarta.validation.constraints.*;

@lombok.Data
public class FamiliaRegisterRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    @Email @NotBlank private String email;
    @NotNull private Integer edad;
    @NotBlank private String distrito;

    @NotBlank private String nombre;
    @NotBlank private String apellido;

    @NotNull @Min(1) private Integer numeroMiembros;
}
