package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    Optional<Usuario> findByTelefono(String telefono);
}
