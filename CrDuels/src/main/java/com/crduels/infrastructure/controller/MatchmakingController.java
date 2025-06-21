package com.crduels.infrastructure.controller;

import com.crduels.application.dto.MatchResultDto;
import com.crduels.application.service.MatchmakingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/matchmaking")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    public MatchmakingController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @PostMapping("/ejecutar")
    public ResponseEntity<List<MatchResultDto>> ejecutar() {
        log.debug("Ejecutando matchmaking desde API");
        List<MatchResultDto> resultados = matchmakingService.ejecutarMatchmaking();
        log.debug("Matchmaking genero {} resultados", resultados.size());
        return ResponseEntity.ok(resultados);
    }
}
