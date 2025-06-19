package com.crduels.infrastructure.controller;

import com.crduels.application.dto.MatchResultDto;
import com.crduels.application.service.MatchmakingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matchmaking")
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    public MatchmakingController(MatchmakingService matchmakingService) {
        this.matchmakingService = matchmakingService;
    }

    @PostMapping
    public ResponseEntity<List<MatchResultDto>> ejecutarMatch() {
        List<MatchResultDto> resultados = matchmakingService.ejecutarMatchmaking();
        return ResponseEntity.ok(resultados);
    }
}
