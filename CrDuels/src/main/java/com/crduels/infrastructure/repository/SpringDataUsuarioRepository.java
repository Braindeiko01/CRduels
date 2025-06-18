package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataUsuarioRepository extends JpaRepository<Usuario, UUID> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
}
