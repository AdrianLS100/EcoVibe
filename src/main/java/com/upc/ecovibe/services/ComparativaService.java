package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.ComparativaPersonalDTO;
import com.upc.ecovibe.dtos.ReporteDTO;
import com.upc.ecovibe.interfaces.IComparativaService;
import com.upc.ecovibe.interfaces.IReporteService;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ComparativaService implements IComparativaService {

    @Autowired
    private IReporteService reporteService;
    @Autowired
    private UserRepository userRepo;

    @Override
    public ComparativaPersonalDTO compararPersonalDia(Long usuarioId,
                                                      LocalDate fecha,
                                                      List<Long> amigosIds,
                                                      String distrito) {

        ReporteDTO miReporte = reporteService.reporteDia(usuarioId, fecha);
        BigDecimal miTotal = nullSafe(miReporte.getTotalKgCO2e());

        BigDecimal promedioAmigos = null;
        int amigosCount = 0;
        if (amigosIds != null && !amigosIds.isEmpty()) {
            BigDecimal suma = BigDecimal.ZERO;
            int n = 0;
            for (Long amigoId : amigosIds) {
                try {
                    ReporteDTO r = reporteService.reporteDia(amigoId, fecha);
                    suma = suma.add(nullSafe(r.getTotalKgCO2e()));
                    n++;
                } catch (Exception ignore) {
                }
            }
            if (n > 0) {
                promedioAmigos = suma.divide(BigDecimal.valueOf(n), 3, java.math.RoundingMode.HALF_UP);
                amigosCount = n;
            }
        }

        BigDecimal promedioRegional = null;
        int regionalCount = 0;
        String distritoTarget = distrito;
        if (distritoTarget == null || distritoTarget.isBlank()) {
            User me = userRepo.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + usuarioId));
            distritoTarget = me.getDistrito();
        }
        if (distritoTarget != null && !distritoTarget.isBlank()) {
            List<User> users = userRepo.findByDistritoIgnoreCase(distritoTarget);
            BigDecimal suma = BigDecimal.ZERO;
            int n = 0;
            for (User u : users) {
                try {
                    ReporteDTO r = reporteService.reporteDia(u.getId(), fecha);
                    suma = suma.add(nullSafe(r.getTotalKgCO2e()));
                    n++;
                } catch (Exception ignore) {
                    // sin actividad ese día
                }
            }
            if (n > 0) {
                promedioRegional = suma.divide(BigDecimal.valueOf(n), 3, java.math.RoundingMode.HALF_UP);
                regionalCount = n;
            }
        }

        return ComparativaPersonalDTO.builder()
                .usuarioId(usuarioId)
                .fecha(fecha)
                .miTotalKgCO2e(miTotal.setScale(3, java.math.RoundingMode.HALF_UP))
                .promedioAmigosKgCO2e(promedioAmigos)
                .amigosConsiderados(amigosCount)
                .promedioRegionalKgCO2e(promedioRegional)
                .usuariosRegionales(regionalCount)
                .build();
    }

    private static BigDecimal nullSafe(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
