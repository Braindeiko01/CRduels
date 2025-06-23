package com.crduels.application.controller;

import com.crduels.application.service.MatchmakingService;
import com.crduels.infrastructure.dto.rq.SolicitudDueloRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/matchmaking")
@RequiredArgsConstructor
public class MatchmakingController {

    private final MatchmakingService matchmakingService;

    @PostMapping("/ejecutar")
    public ResponseEntity<?> ejecutarMatchmaking(@RequestBody SolicitudDueloRequest request) {
        return matchmakingService.intentarEmparejar(request.getUsuarioId(), request.getModoJuego())
                .map(partida -> {
                    Map<String, Object> match = new HashMap<>();
                    match.put("matchEncontrado", true);
                    match.put("matchId", partida.getId());
                    match.put("chatId", partida.getChatId());
                    match.put("jugador1", partida.getJugador1Id());
                    match.put("jugador2", partida.getJugador2Id());
                    return ResponseEntity.ok(match);
                })
                .orElseGet(() -> {
                    Map<String, Object> sinMatch = new HashMap<>();
                    sinMatch.put("matchEncontrado", false);
                    sinMatch.put("mensaje", "Esperando oponente...");
                    return ResponseEntity.ok(sinMatch);
                });
    }
}
