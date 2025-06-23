package com.crduels.application.controller;


import com.crduels.application.service.MatchmakingService;
import com.crduels.infrastructure.dto.rq.SolicitudDueloRequest;
import com.crduels.domain.entity.DueloRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/duelo")
@RequiredArgsConstructor
public class DueloController {

    private final MatchmakingService matchmakingService;

    @PostMapping("/solicitar")
    public ResponseEntity<?> solicitarDuelo(@RequestBody SolicitudDueloRequest request) {
        DueloRequest duelo = matchmakingService.registrarSolicitudDuelo(
                request.getUsuarioId(), request.getModoJuego());

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("mensaje", "Solicitud registrada.");
        respuesta.put("dueloRequestId", duelo.getId());
        return ResponseEntity.ok(respuesta);
    }
}
