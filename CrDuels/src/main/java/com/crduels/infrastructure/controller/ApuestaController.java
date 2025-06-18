package com.crduels.infrastructure.controller;

import com.crduels.application.dto.ApuestaRequestDto;
import com.crduels.application.dto.ApuestaResponseDto;
import com.crduels.application.service.ApuestaService;
import com.crduels.domain.model.EstadoApuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/apuestas")
public class ApuestaController {

    private final ApuestaService apuestaService;

    public ApuestaController(ApuestaService apuestaService) {
        this.apuestaService = apuestaService;
    }

    @PostMapping
    public ResponseEntity<ApuestaResponseDto> crear(@RequestBody ApuestaRequestDto dto) {
        ApuestaResponseDto response = apuestaService.crearApuesta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<ApuestaResponseDto>> listarPendientes(@RequestParam("modo") String modo) {
        List<ApuestaResponseDto> lista = apuestaService.listarPendientesPorModo(modo);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ApuestaResponseDto> cambiarEstado(@PathVariable UUID id,
                                                            @RequestParam("estado") EstadoApuesta estado) {
        ApuestaResponseDto response = apuestaService.cambiarEstado(id, estado);
        return ResponseEntity.ok(response);
    }
}
