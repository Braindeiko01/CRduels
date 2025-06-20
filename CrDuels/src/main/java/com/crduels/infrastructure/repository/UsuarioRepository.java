package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    java.util.Optional<Usuario> findByTelefono(String telefono);
}
