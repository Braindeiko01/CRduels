package com.crduels.infrastructure.controller;

import com.crduels.application.dto.TransaccionRequestDto;
import com.crduels.application.dto.TransaccionResponseDto;
import com.crduels.application.service.TransaccionService;
import com.crduels.domain.model.EstadoTransaccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping
    public ResponseEntity<TransaccionResponseDto> crear(@RequestBody TransaccionRequestDto dto) {
        TransaccionResponseDto response = transaccionService.registrarTransaccion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<TransaccionResponseDto>> listarPorUsuario(@PathVariable("id") String usuarioId) {
        List<TransaccionResponseDto> lista = transaccionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<TransaccionResponseDto> cambiarEstado(@PathVariable UUID id,
                                                                @RequestParam("estado") EstadoTransaccion estado) {
        TransaccionResponseDto response = transaccionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(response);
    }
}
