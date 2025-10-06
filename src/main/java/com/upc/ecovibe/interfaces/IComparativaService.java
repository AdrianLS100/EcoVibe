package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.ComparativaPersonalDTO;

import java.time.LocalDate;
import java.util.List;

public interface IComparativaService {
    ComparativaPersonalDTO compararPersonalDia(Long usuarioId,
                                               LocalDate fecha,
                                               List<Long> amigosIds,
                                               String distrito);
}
