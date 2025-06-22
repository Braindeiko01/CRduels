package com.crduels.application.controller;

import com.crduels.infrastructure.dto.rq.MatchmakingJoinRequest;
import com.crduels.application.service.RealtimeMatchmakingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Map;

@RestController
@RequestMapping("/api/matchmaking")
@Tag(name = "Matchmaking", description = "Proceso de emparejamiento")
@RequiredArgsConstructor
public class MatchmakingController {

    private final RealtimeMatchmakingService realtimeMatchmakingService;

    @PostMapping("/ejecutar")
    @Operation(summary = "Ejecutar", description = "Registra un jugador en el matchmaking")
    public ResponseEntity<Map<String, String>> ejecutar(@RequestBody MatchmakingJoinRequest dto) {
        boolean match = realtimeMatchmakingService.joinQueue(dto);
        String status = match ? "emparejado" : "esperando";
        return ResponseEntity.ok(Map.of("status", status));
    }

    @PostMapping("/cancelar")
    @Operation(summary = "Cancelar", description = "Cancela la espera del jugador")
    public ResponseEntity<Void> cancelar(@RequestBody Map<String, String> body) {
        String usuarioId = body.get("usuarioId");
        if (usuarioId != null) {
            realtimeMatchmakingService.cancelar(usuarioId);
        }
        return ResponseEntity.ok().build();
    }
}
