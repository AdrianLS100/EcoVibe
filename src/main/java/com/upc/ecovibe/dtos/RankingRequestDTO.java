package com.upc.ecovibe.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public record RankingRequestDTO(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
        String distrito,
        List<Long> amigosIds,
        Long usuarioId
) {}
