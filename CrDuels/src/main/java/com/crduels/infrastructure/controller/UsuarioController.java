package com.crduels.infrastructure.controller;

import com.crduels.application.dto.UsuarioDto;
import com.crduels.application.mapper.UsuarioMapper;
import com.crduels.application.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper mapper) {
        this.usuarioService = usuarioService;
        this.mapper = mapper;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDto> registrar(@Valid @RequestBody UsuarioDto usuario) {
        var entity = mapper.toEntity(usuario);
        var nuevo = usuarioService.registrarUsuario(entity);
        return ResponseEntity.ok(mapper.toDto(nuevo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtener(@PathVariable UUID id) {
        return usuarioService.obtenerPorId(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
