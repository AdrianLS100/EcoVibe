package com.upc.ecovibe.services;

import com.upc.ecovibe.dtos.RankingDTO;
import com.upc.ecovibe.dtos.RankingRequestDTO;
import com.upc.ecovibe.interfaces.IReporteService;
import com.upc.ecovibe.security.entities.User;
import com.upc.ecovibe.security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class RankingService {

    private final UserRepository userRepo;
    private final IReporteService reporteService;

    public RankingService(UserRepository userRepo, IReporteService reporteService) {
        this.userRepo = userRepo;
        this.reporteService = reporteService;
    }

    public List<RankingDTO> rankingPersonal(RankingRequestDTO req) {
        LocalDate fecha = Optional.ofNullable(req.fecha())
                .orElse(LocalDate.now());

        List<User> universo;
        if (req.amigosIds() != null && !req.amigosIds().isEmpty()) {
            universo = userRepo.findAllById(req.amigosIds());
        } else if (req.distrito() != null && !req.distrito().isBlank()) {
            universo = userRepo.findByDistritoIgnoreCase(req.distrito());
        } else {
            universo = userRepo.findAll();
        }

        List<RankingDTO> filas = new ArrayList<>();
        for (User u : universo) {
            BigDecimal total;
            try {
                var rep = reporteService.reporteDia(u.getId(), fecha);
                total = rep.getTotalKgCO2e();
            } catch (Exception e) {
                // Si el usuario no tiene actividades ese día, lo dejamos en 0
                total = BigDecimal.ZERO;
            }
            filas.add(new RankingDTO(u.getId(), u.getUsername(), u.getDistrito(), total, 0));
        }

        filas.sort(Comparator.comparing(RankingDTO::getTotalKgCO2e));

        AtomicInteger pos = new AtomicInteger(1);
        filas.forEach(f -> f.setPuesto(pos.getAndIncrement()));

        return filas;
    }
}
