package com.upc.ecovibe.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ComparativaPersonalRequest {
    @NotNull private Long usuarioId;
    @NotNull private LocalDate fecha;

    private List<Long> amigosIds;
    private String distrito;
}
