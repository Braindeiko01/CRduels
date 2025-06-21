package com.crduels.application.controller;

import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rs.ApuestaResponse;
import com.crduels.application.service.ApuestaService;
import com.crduels.domain.entity.EstadoApuesta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/apuestas")
@Tag(name = "Apuestas", description = "Administración de apuestas")
public class ApuestaController {

    private final ApuestaService apuestaService;

    public ApuestaController(ApuestaService apuestaService) {
        this.apuestaService = apuestaService;
    }

    @PostMapping
    @Operation(summary = "Crear apuesta", description = "Registra una nueva apuesta")
    public ResponseEntity<ApuestaResponse> crear(@RequestBody ApuestaRequest dto) {
        ApuestaResponse response = apuestaService.crearApuesta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/pendientes")
    @Operation(summary = "Listar pendientes", description = "Obtiene apuestas pendientes por modo de juego")
    public ResponseEntity<List<ApuestaResponse>> listarPendientes(@RequestParam("modo") String modo) {
        List<ApuestaResponse> lista = apuestaService.listarPendientesPorModo(modo);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una apuesta")
    public ResponseEntity<ApuestaResponse> cambiarEstado(@PathVariable UUID id,
                                                         @RequestParam("estado") EstadoApuesta estado) {
        ApuestaResponse response = apuestaService.cambiarEstado(id, estado);
        return ResponseEntity.ok(response);
    }
}
