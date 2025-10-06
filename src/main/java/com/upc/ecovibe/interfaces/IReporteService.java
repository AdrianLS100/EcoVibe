package com.upc.ecovibe.interfaces;

import com.upc.ecovibe.dtos.ReporteDTO;
import com.upc.ecovibe.dtos.ReporteFamiliaDTO;
import com.upc.ecovibe.dtos.ReporteInstitucionDTO;

import java.time.LocalDate;

public interface IReporteService {
    ReporteDTO reporteDia(Long usuarioId, LocalDate fecha);

    ReporteFamiliaDTO reporteFamiliaDia(Long familiaId, LocalDate fecha);

    ReporteInstitucionDTO reporteInstitucionDia(Long institucionId, LocalDate fecha);


}
