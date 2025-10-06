package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.CalculadoraFamiliarDTO;
import com.upc.ecovibe.dtos.CalculadoraPersonalDTO;
import com.upc.ecovibe.dtos.CalculadoraInstitucionDTO;
import com.upc.ecovibe.entities.ActividadesDiarias;
import com.upc.ecovibe.entities.FactoresEmision;
import com.upc.ecovibe.interfaces.ICalculadoraService;
import com.upc.ecovibe.repositories.ActividadesDiariasRepository;
import com.upc.ecovibe.repositories.FactoresEmisionRepository;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CalculadoraService implements ICalculadoraService {

    @Autowired private FactoresEmisionRepository factoresRepo;
    @Autowired private ActividadesDiariasRepository actividadesRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private ModelMapper modelMapper;

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    // =======================
    // Personal
    // =======================
    @Override
    public CalculadoraPersonalDTO calcularp(CalculadoraPersonalDTO request) {
        BigDecimal total = BigDecimal.ZERO;

        if (request.getHorasBusSemana() != null) {
            total = total.add(request.getHorasBusSemana()
                    .multiply(getFactor("transporte", "bus", "km")));
        }
        if (request.getKwhMes() != null) {
            total = total.add(request.getKwhMes()
                    .multiply(getFactor("energia", "electricidad", "kWh")));
        }
        if (request.getDiasCarnePorSemana() != null) {
            total = total.add(BigDecimal.valueOf(request.getDiasCarnePorSemana())
                    .multiply(getFactor("alimentacion", "carne", "porcion")));
        }

        request.setTotalKgCO2e(total);
        return request;
    }

    // =======================
    // Familiar
    // =======================
    @Override
    public CalculadoraFamiliarDTO calcularf(CalculadoraFamiliarDTO request) {
        BigDecimal total = BigDecimal.ZERO;

        if (request.getKwhMes() != null) {
            total = total.add(request.getKwhMes()
                    .multiply(getFactor("energia", "electricidad", "kWh")));
        }
        if (request.getBalonesGlp10kgMes() != null) {
            total = total.add(BigDecimal.valueOf(request.getBalonesGlp10kgMes() * 10)
                    .multiply(getFactor("energia", "glp", "kg")));
        }
        if (request.getDiasCarnePorSemana() != null && request.getNumeroPersonas() != null) {
            total = total.add(BigDecimal.valueOf(request.getDiasCarnePorSemana() * request.getNumeroPersonas())
                    .multiply(getFactor("alimentacion", "carne", "porcion")));
        }

        request.setTotalKgCO2e(total);
        return request;
    }

    // =======================
    // Institución
    // =======================
    @Override
    public CalculadoraInstitucionDTO calculari(CalculadoraInstitucionDTO req) {

        BigDecimal kwhMes = nz(req.getKwhMes());
        BigDecimal glpKg  = nz(req.getGlpKg());
        BigDecimal kmBus  = nz(req.getKmBusEscolar());
        BigDecimal kmVan  = nz(req.getKmVanEscolar());
        BigDecimal kmAuto = nz(req.getKmAutoDocentes());
        BigDecimal resKg  = nz(req.getResiduosKg());

        BigDecimal fKwh  = getFactor("energia", "electricidad", "kWh");
        BigDecimal fGlp  = getFactor("energia", "glp", "kg");
        BigDecimal fBus  = getFactor("transporte", "bus", "km");          // <— ajustado
        BigDecimal fVan  = getFactor("transporte", "van", "km");          // <— ajustado (o combi)
        BigDecimal fAuto = getFactor("transporte", "auto", "km");         // <— ajustado
        BigDecimal fRes  = getFactor("residuos", "general", "kg");

        BigDecimal energiaKg = kwhMes.divide(BigDecimal.valueOf(30), 6, RoundingMode.HALF_UP).multiply(fKwh)
                .add(glpKg.multiply(fGlp));

        BigDecimal transporteKg = kmBus.multiply(fBus)
                .add(kmVan.multiply(fVan))
                .add(kmAuto.multiply(fAuto));

        BigDecimal residuosKgCO2e = resKg.multiply(fRes);
        BigDecimal total = energiaKg.add(transporteKg).add(residuosKgCO2e);

        return CalculadoraInstitucionDTO.builder()
                .fecha(req.getFecha())
                .kwhMes(kwhMes)
                .glpKg(glpKg)
                .kmBusEscolar(kmBus)
                .kmVanEscolar(kmVan)
                .kmAutoDocentes(kmAuto)
                .residuosKg(resKg)
                .energiaKgCO2e(energiaKg.setScale(3, RoundingMode.HALF_UP))
                .transporteKgCO2e(transporteKg.setScale(3, RoundingMode.HALF_UP))
                .residuosKgCO2e(residuosKgCO2e.setScale(3, RoundingMode.HALF_UP))
                .totalKgCO2e(total.setScale(3, RoundingMode.HALF_UP))
                .build();
    }


    private BigDecimal getFactor(String categoria, String subcategoria, String unidadBase) {
        FactoresEmision factor = factoresRepo
                .findFirstByCategoriaIgnoreCaseAndSubcategoriaIgnoreCaseAndUnidadBaseIgnoreCaseAndVigenteTrue(
                        categoria, subcategoria, unidadBase
                )
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Falta factor " + categoria + "/" + subcategoria + " (" + unidadBase + ")"
                ));
        return factor.getFactorKgco2ePerUnidad();
    }

    private static BigDecimal nz(BigDecimal v) { return v != null ? v : ZERO; }
}
