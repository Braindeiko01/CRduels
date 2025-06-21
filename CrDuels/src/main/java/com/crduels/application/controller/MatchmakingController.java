package com.crduels.application.controller;

import com.crduels.infrastructure.dto.MatchResultDto;
import com.crduels.application.service.MatchmakingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/matchmaking")
@Tag(name = "Matchmaking", description = "Proceso de emparejamiento")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    public MatchmakingController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @PostMapping("/ejecutar")
    @Operation(summary = "Ejecutar", description = "Ejecuta el proceso de matchmaking")
    public ResponseEntity<List<MatchResultDto>> ejecutar() {
        List<MatchResultDto> resultados = matchmakingService.ejecutarMatchmaking();
        return ResponseEntity.ok(resultados);
    }
}
