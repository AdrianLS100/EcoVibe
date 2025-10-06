package com.upc.ecovibe.controllers;

import com.upc.ecovibe.dtos.RankingDTO;
import com.upc.ecovibe.dtos.RankingRequestDTO;
import com.upc.ecovibe.services.RankingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER','PERSONAL','FAMILIAR')")
    @PostMapping("/personal")
    public ResponseEntity<List<RankingDTO>> rankingPersonal(@Valid @RequestBody RankingRequestDTO req) {
        return ResponseEntity.ok(rankingService.rankingPersonal(req));
    }
}
