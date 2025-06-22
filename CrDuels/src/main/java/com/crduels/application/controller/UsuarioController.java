package com.crduels.application.controller;

import com.crduels.application.service.UsuarioService;
import com.crduels.infrastructure.dto.rq.UsuarioRequest;
import com.crduels.infrastructure.dto.rs.UsuarioResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuarios", description = "Operaciones de usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PutMapping
    @Operation(summary = "Registrar usuario", description = "Registra un nuevo usuario")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest usuario) {
        UsuarioResponse nuevo = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario", description = "Obtiene un usuario por su identificador")
    public ResponseEntity<UsuarioResponse> obtener(@PathVariable String id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
