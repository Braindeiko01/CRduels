package com.crduels.infrastructure.controller;

import com.crduels.application.dto.PartidaRequestDto;
import com.crduels.application.dto.PartidaResponseDto;
import com.crduels.application.service.PartidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<PartidaResponseDto> registrar(@RequestBody PartidaRequestDto dto) {
        log.debug("Registrando partida via API: {}", dto);
        PartidaResponseDto response = partidaService.registrarPartida(dto);
        log.debug("Partida registrada {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/apuesta/{apuestaId}")
    public ResponseEntity<PartidaResponseDto> obtenerPorApuesta(@PathVariable UUID apuestaId) {
        log.debug("Obteniendo partida por apuesta {}", apuestaId);
        return partidaService.obtenerPorApuestaId(apuestaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/validar")
    public ResponseEntity<PartidaResponseDto> validar(@PathVariable UUID id) {
        log.debug("Validando partida {}", id);
        PartidaResponseDto response = partidaService.marcarComoValidada(id);
        return ResponseEntity.ok(response);
    }
}
