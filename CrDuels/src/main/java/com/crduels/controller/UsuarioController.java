package com.crduels.controller;

import com.crduels.entity.Usuario;
import com.crduels.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

  @PostMapping("/registro")
public ResponseEntity<?> registrar(@Valid @RequestBody Usuario usuario) {
    try {
        Usuario nuevo = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // 201 creado
    } catch (DuplicateUserException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); // 409 conflicto
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage()); // 400 error de validación
    }
}

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable UUID id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
