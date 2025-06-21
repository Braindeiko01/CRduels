package com.crduels.application.controller;

import com.crduels.infrastructure.dto.PartidaRequestDto;
import com.crduels.infrastructure.dto.PartidaResponseDto;
import com.crduels.infrastructure.service.PartidaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@RestController
@RequestMapping("/api/partidas")
@Tag(name = "Partidas", description = "Gestión de partidas")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    @Operation(summary = "Registrar partida", description = "Registra el resultado de una apuesta")
    public ResponseEntity<PartidaResponseDto> registrar(@RequestBody PartidaRequestDto dto) {
        PartidaResponseDto response = partidaService.registrarPartida(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/apuesta/{apuestaId}")
    @Operation(summary = "Buscar por apuesta", description = "Obtiene la partida asociada a una apuesta")
    public ResponseEntity<PartidaResponseDto> obtenerPorApuesta(@PathVariable UUID apuestaId) {
        return partidaService.obtenerPorApuestaId(apuestaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/validar")
    @Operation(summary = "Validar", description = "Marca una partida como validada y reparte el premio")
    public ResponseEntity<PartidaResponseDto> validar(@PathVariable UUID id) {
        PartidaResponseDto response = partidaService.marcarComoValidada(id);
        return ResponseEntity.ok(response);
    }
}
