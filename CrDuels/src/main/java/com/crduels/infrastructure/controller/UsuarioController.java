package com.crduels.infrastructure.controller;

import com.crduels.application.dto.UsuarioDto;
import com.crduels.application.service.UsuarioService;
import com.crduels.application.exception.DuplicateUserException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@Valid @RequestBody UsuarioDto usuario) {
        try {
            UsuarioDto nuevo = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (DuplicateUserException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtener(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
