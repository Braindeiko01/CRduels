package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmail(String email);
    boolean existsByTelefono(String telefono);
    java.util.Optional<Usuario> findByTelefono(String telefono);
    java.util.Optional<Usuario> findByGoogleId(String googleId);
    boolean existsByGoogleId(String googleId);
}
