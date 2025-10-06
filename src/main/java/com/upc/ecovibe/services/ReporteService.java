package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.ReporteDTO;
import com.upc.ecovibe.dtos.ReporteFamiliaDTO;
import com.upc.ecovibe.dtos.ReporteInstitucionDTO;
import com.upc.ecovibe.entities.Alimentacion;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.interfaces.*;
import com.upc.ecovibe.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReporteService implements IReporteService {

    @Autowired private IActividadesDiariasService actividadesService;
    @Autowired private TransporteRepository transporteRepository;
    @Autowired private EnergiaRepository energiaRepository;
    @Autowired private ResiduoRepository residuoRepository;
    @Autowired private AlimentacionRepository alimentacionRepository;
    @Autowired private IFactoresEmisionService factoresEmisionService;

    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static BigDecimal nz(BigDecimal v){ return v != null ? v : ZERO; }

    @Override
    public ReporteDTO reporteDia(Long usuarioId, LocalDate fecha) {
        ActividadesDiarias actividad = actividadesService
                .obtenerPorUsuarioYFecha(usuarioId, fecha)
                .map(dto -> {
                    ActividadesDiarias a = new ActividadesDiarias();
                    a.setId(dto.getId());
                    return a;
                })
                .orElseThrow(() -> new RuntimeException("No existe actividad para usuario " + usuarioId + " en " + fecha));

        Long actividadId = actividad.getId();

        BigDecimal kmBus  = nz(transporteRepository.sumDistanciaByActividadAndMedio(actividadId, "bus"));
        BigDecimal kmTren = nz(transporteRepository.sumDistanciaByActividadAndMedio(actividadId, "tren"));
        BigDecimal kmMet  = nz(transporteRepository.sumDistanciaByActividadAndMedio(actividadId, "metropolitano"));
        BigDecimal kmAuto = nz(transporteRepository.sumDistanciaByActividadAndMedio(actividadId, "auto"));

        BigDecimal fBus  = factoresEmisionService.buscarVigente("transporte", "bus", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fTren = factoresEmisionService.buscarVigente("transporte", "tren", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fMet  = factoresEmisionService.buscarVigente("transporte", "metropolitano", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fAuto = factoresEmisionService.buscarVigente("transporte", "auto", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);

        BigDecimal transporteKg = kmBus.multiply(fBus)
                .add(kmTren.multiply(fTren))
                .add(kmMet.multiply(fMet))
                .add(kmAuto.multiply(fAuto));

        BigDecimal kwhMes = nz(energiaRepository.sumConsumoByActividadAndTipo(actividadId, "electricidad"));
        BigDecimal glpKg  = nz(energiaRepository.sumConsumoByActividadAndTipo(actividadId, "glp"));

        BigDecimal fKwh = factoresEmisionService.buscarVigente("energia", "electricidad", "kWh")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fGlp = factoresEmisionService.buscarVigente("energia", "glp", "kg")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);

        BigDecimal energiaKg = nz(kwhMes.divide(BigDecimal.valueOf(30), 6, java.math.RoundingMode.HALF_UP).multiply(fKwh))
                .add(glpKg.multiply(fGlp));

        Optional<Alimentacion> alimOpt = alimentacionRepository.findTopByActividad_IdOrderByIdDesc(actividadId);
        String dieta = alimOpt.map(Alimentacion::getTipoDieta).orElse(null);
        BigDecimal alimentacionKg = ZERO;
        if (dieta != null && !dieta.isBlank()) {
            BigDecimal fDietaDia = factoresEmisionService.buscarVigente("alimentacion", dieta.toLowerCase(), "dia")
                    .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
            alimentacionKg = fDietaDia; // 1 día
        }

        BigDecimal residuosKg = nz(residuoRepository.sumPesoByActividad(actividadId));
        BigDecimal fRes = factoresEmisionService.buscarVigente("residuos", "general", "kg")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal residuosEmision = residuosKg.multiply(fRes);

        BigDecimal total = transporteKg.add(energiaKg).add(alimentacionKg).add(residuosEmision);

        return ReporteDTO.builder()
                .usuarioId(usuarioId)
                .fecha(fecha)
                .transporteKgCO2e(transporteKg.setScale(3, java.math.RoundingMode.HALF_UP))
                .energiaKgCO2e(energiaKg.setScale(3, java.math.RoundingMode.HALF_UP))
                .alimentacionKgCO2e(alimentacionKg.setScale(3, java.math.RoundingMode.HALF_UP))
                .residuosKgCO2e(residuosEmision.setScale(3, java.math.RoundingMode.HALF_UP))
                .totalKgCO2e(total.setScale(3, java.math.RoundingMode.HALF_UP))
                .build();
    }

    @Override
    public ReporteFamiliaDTO reporteFamiliaDia(Long familiaId, LocalDate fecha) {
        final var ZERO = java.math.BigDecimal.ZERO;

        BigDecimal kmBus  = nz(transporteRepository.sumDistanciaByFamiliaFechaMedio(familiaId, fecha, "bus"));
        BigDecimal kmTren = nz(transporteRepository.sumDistanciaByFamiliaFechaMedio(familiaId, fecha, "tren"));
        BigDecimal kmMet  = nz(transporteRepository.sumDistanciaByFamiliaFechaMedio(familiaId, fecha, "metropolitano"));
        BigDecimal kmAuto = nz(transporteRepository.sumDistanciaByFamiliaFechaMedio(familiaId, fecha, "auto"));

        BigDecimal fBus  = factoresEmisionService.buscarVigente("transporte", "bus", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fTren = factoresEmisionService.buscarVigente("transporte", "tren", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fMet  = factoresEmisionService.buscarVigente("transporte", "metropolitano", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fAuto = factoresEmisionService.buscarVigente("transporte", "auto", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);

        BigDecimal transporteKg = kmBus.multiply(fBus)
                .add(kmTren.multiply(fTren))
                .add(kmMet.multiply(fMet))
                .add(kmAuto.multiply(fAuto));

        // ---------- ENERGÍA ----------
        BigDecimal kwhMes = nz(energiaRepository.sumConsumoByFamiliaFechaAndTipo(familiaId, fecha, "electricidad"));
        BigDecimal glpKg  = nz(energiaRepository.sumConsumoByFamiliaFechaAndTipo(familiaId, fecha, "glp"));

        BigDecimal fKwh = factoresEmisionService.buscarVigente("energia", "electricidad", "kWh")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal fGlp = factoresEmisionService.buscarVigente("energia", "glp", "kg")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);

        BigDecimal energiaKg = nz(kwhMes.divide(java.math.BigDecimal.valueOf(30), 6, java.math.RoundingMode.HALF_UP).multiply(fKwh))
                .add(glpKg.multiply(fGlp));

        BigDecimal alimentacionKg = ZERO;
        var grupos = alimentacionRepository.countAlimentacionByFamiliaFechaGroupTipo(familiaId, fecha);
        for (Object[] row : grupos) {
            String tipo = (String) row[0];
            long count = (long) row[1];
            BigDecimal fDietaDia = factoresEmisionService.buscarVigente("alimentacion", tipo, "dia")
                    .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
            alimentacionKg = alimentacionKg.add(fDietaDia.multiply(java.math.BigDecimal.valueOf(count)));
        }

        BigDecimal residuosKg = nz(residuoRepository.sumPesoByFamiliaAndFecha(familiaId, fecha));
        BigDecimal fRes = factoresEmisionService.buscarVigente("residuos", "general", "kg")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        BigDecimal residuosEmision = residuosKg.multiply(fRes);

        BigDecimal total = transporteKg.add(energiaKg).add(alimentacionKg).add(residuosEmision);

        return ReporteFamiliaDTO.builder()
                .familiaId(familiaId)
                .fecha(fecha)
                .transporteKgCO2e(transporteKg.setScale(3, java.math.RoundingMode.HALF_UP))
                .energiaKgCO2e(energiaKg.setScale(3, java.math.RoundingMode.HALF_UP))
                .alimentacionKgCO2e(alimentacionKg.setScale(3, java.math.RoundingMode.HALF_UP))
                .residuosKgCO2e(residuosEmision.setScale(3, java.math.RoundingMode.HALF_UP))
                .totalKgCO2e(total.setScale(3, java.math.RoundingMode.HALF_UP))
                .build();
    }

    @Override
    public ReporteInstitucionDTO reporteInstitucionDia(Long institucionId, LocalDate fecha) {
        final var ZERO = java.math.BigDecimal.ZERO;

        var kmBus  = nz(transporteRepository.sumDistanciaByInstitucionFechaMedio(institucionId, fecha, "bus"));
        var kmVan  = nz(transporteRepository.sumDistanciaByInstitucionFechaMedio(institucionId, fecha, "van"));
        var kmAuto = nz(transporteRepository.sumDistanciaByInstitucionFechaMedio(institucionId, fecha, "auto"));

        var fBus  = factoresEmisionService.buscarVigente("transporte", "bus", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        var fVan  = factoresEmisionService.buscarVigente("transporte", "van", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        var fAuto = factoresEmisionService.buscarVigente("transporte", "auto", "km")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);

        var transporteKg = kmBus.multiply(fBus)
                .add(kmVan.multiply(fVan))
                .add(kmAuto.multiply(fAuto));

        var kwhMes = nz(energiaRepository.sumConsumoByInstitucionFechaAndTipo(institucionId, fecha, "electricidad"));
        var glpKg  = nz(energiaRepository.sumConsumoByInstitucionFechaAndTipo(institucionId, fecha, "glp"));

        var fKwh = factoresEmisionService.buscarVigente("energia", "electricidad", "kWh")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        var fGlp = factoresEmisionService.buscarVigente("energia", "glp", "kg")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);

        var energiaKg = nz(kwhMes.divide(java.math.BigDecimal.valueOf(30), 6, RoundingMode.HALF_UP).multiply(fKwh))
                .add(glpKg.multiply(fGlp));

        var alimentacionKg = ZERO;
        var grupos = alimentacionRepository.countAlimentacionByInstitucionFechaGroupTipo(institucionId, fecha);
        for (Object[] row : grupos) {
            String tipo = (String) row[0];      // "omnivora" | "vegetariana" | "vegana" | etc.
            long count  = (long) row[1];
            var fDietaDia = factoresEmisionService.buscarVigente("alimentacion", tipo, "dia")
                    .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
            alimentacionKg = alimentacionKg.add(fDietaDia.multiply(java.math.BigDecimal.valueOf(count)));
        }

        var residuosKg = nz(residuoRepository.sumPesoByInstitucionAndFecha(institucionId, fecha));
        var fRes = factoresEmisionService.buscarVigente("residuos", "general", "kg")
                .map(x -> x.getFactorKgco2ePerUnidad()).orElse(ZERO);
        var residuosEmision = residuosKg.multiply(fRes);

        var total = transporteKg.add(energiaKg).add(alimentacionKg).add(residuosEmision);

        return ReporteInstitucionDTO.builder()
                .institucionId(institucionId)
                .fecha(fecha)
                .transporteKgCO2e(transporteKg.setScale(3, RoundingMode.HALF_UP))
                .energiaKgCO2e(energiaKg.setScale(3, RoundingMode.HALF_UP))
                .alimentacionKgCO2e(alimentacionKg.setScale(3, RoundingMode.HALF_UP))
                .residuosKgCO2e(residuosEmision.setScale(3, RoundingMode.HALF_UP))
                .totalKgCO2e(total.setScale(3, RoundingMode.HALF_UP))
                .build();
    }

}
