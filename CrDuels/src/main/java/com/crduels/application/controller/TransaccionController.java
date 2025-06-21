package com.crduels.application.controller;

import com.crduels.infrastructure.dto.TransaccionRequestDto;
import com.crduels.infrastructure.dto.TransaccionResponseDto;
import com.crduels.application.service.TransaccionService;
import com.crduels.domain.entity.EstadoTransaccion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transacciones")
@Tag(name = "Transacciones", description = "Movimientos de saldo")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PostMapping
    @Operation(summary = "Registrar transacción", description = "Crea una nueva transacción")
    public ResponseEntity<TransaccionResponseDto> crear(@RequestBody TransaccionRequestDto dto) {
        TransaccionResponseDto response = transaccionService.registrarTransaccion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Listar por usuario", description = "Obtiene las transacciones de un usuario")
    public ResponseEntity<List<TransaccionResponseDto>> listarPorUsuario(@PathVariable("id") String usuarioId) {
        List<TransaccionResponseDto> lista = transaccionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una transacción")
    public ResponseEntity<TransaccionResponseDto> cambiarEstado(@PathVariable UUID id,
                                                                @RequestParam("estado") EstadoTransaccion estado) {
        TransaccionResponseDto response = transaccionService.cambiarEstado(id, estado);
        return ResponseEntity.ok(response);
    }
}
