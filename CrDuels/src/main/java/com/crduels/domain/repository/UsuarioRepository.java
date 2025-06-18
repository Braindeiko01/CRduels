package com.crduels.domain.repository;

import com.crduels.domain.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
}
