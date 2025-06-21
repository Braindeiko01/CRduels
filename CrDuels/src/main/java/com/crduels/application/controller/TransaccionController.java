package com.crduels.application.controller;

import com.crduels.application.service.TransaccionService;
import com.crduels.infrastructure.dto.rq.TransaccionRequest;
import com.crduels.infrastructure.dto.rs.TransaccionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transacciones")
@Tag(name = "Transacciones", description = "Movimientos de saldo")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping
    @Operation(summary = "Registrar transacción", description = "Crea una nueva transacción")
    public ResponseEntity<TransaccionResponse> crear(@RequestBody TransaccionRequest dto) {
        TransaccionResponse response = transaccionService.registrarTransaccion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/usuario/{id}")
    @Operation(summary = "Listar por usuario", description = "Obtiene las transacciones de un usuario")
    public ResponseEntity<List<TransaccionResponse>> listarPorUsuario(@PathVariable("id") String usuarioId) {
        List<TransaccionResponse> lista = transaccionService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/{id}/aprobar")
    @Operation(summary = "Aprobar transacción", description = "Aprueba la transacción y actualiza el saldo")
    public ResponseEntity<TransaccionResponse> aprobar(@PathVariable UUID id) {
        TransaccionResponse response = transaccionService.aprobarTransaccion(id);
        return ResponseEntity.ok(response);
    }

}
