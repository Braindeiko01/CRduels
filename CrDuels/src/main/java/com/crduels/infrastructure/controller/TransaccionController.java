package com.crduels.infrastructure.controller;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.application.service.TransaccionService;
import com.crduels.domain.model.EstadoTransaccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping
    public ResponseEntity<TransaccionResponseDto> crear(@RequestBody TransaccionRequestDto dto) {
        log.debug("Registrando transaccion via API: {}", dto);
        TransaccionResponseDto response = transaccionService.registrarTransaccion(dto);
        log.debug("Transaccion registrada {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<TransaccionResponseDto>> listarPorUsuario(@PathVariable("id") String usuarioId) {
        log.debug("Listando transacciones de usuario {}", usuarioId);
        List<TransaccionResponseDto> lista = transaccionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<TransaccionResponseDto> cambiarEstado(@PathVariable UUID id,
                                                                @RequestParam("estado") EstadoTransaccion estado) {
        log.debug("Cambiando estado transaccion {} a {}", id, estado);
        TransaccionResponseDto response = transaccionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(response);
    }
}
