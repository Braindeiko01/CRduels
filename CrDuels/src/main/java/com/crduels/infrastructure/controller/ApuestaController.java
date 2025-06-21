package com.crduels.infrastructure.controller;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.application.service.ApuestaService;
import com.crduels.domain.model.EstadoApuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/apuestas")
public class ApuestaController {

    private final ApuestaService apuestaService;

    public ApuestaController(ApuestaService apuestaService) {
        this.apuestaService = apuestaService;
    }

    @PostMapping
    public ResponseEntity<ApuestaResponseDto> crear(@RequestBody ApuestaRequestDto dto) {
        log.debug("Creando apuesta via API: {}", dto);
        ApuestaResponseDto response = apuestaService.crearApuesta(dto);
        log.debug("Apuesta creada {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<ApuestaResponseDto>> listarPendientes(@RequestParam("modo") String modo) {
        log.debug("Listando pendientes para modo {}", modo);
        List<ApuestaResponseDto> lista = apuestaService.listarPendientesPorModo(modo);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApuestaResponseDto> cambiarEstado(@PathVariable UUID id,
                                                            @RequestParam("estado") EstadoApuesta estado) {
        log.debug("Cambiando estado apuesta {} a {}", id, estado);
        ApuestaResponseDto response = apuestaService.cambiarEstado(id, estado);
        return ResponseEntity.ok(response);
    }
}
