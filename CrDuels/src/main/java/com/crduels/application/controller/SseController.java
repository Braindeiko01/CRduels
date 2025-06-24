package com.crduels.application.controller;

import com.crduels.application.service.SseService;
import com.crduels.application.service.MatchSseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sse")
@RequiredArgsConstructor
public class SseController {

    private final SseService sseService;
    private final MatchSseService matchSseService;

    @GetMapping("/transacciones")
    public SseEmitter streamTransacciones() {
        return sseService.subscribe();
    }

    @GetMapping("/matchmaking/{usuarioId}")
    public SseEmitter streamMatch(@PathVariable("usuarioId") String usuarioId) {
        return matchSseService.subscribe(usuarioId);
    }

    @GetMapping("/match")
    public SseEmitter streamMatchLegacy(@RequestParam("jugadorId") String jugadorId) {
        return matchSseService.subscribe(jugadorId);
    }
}
