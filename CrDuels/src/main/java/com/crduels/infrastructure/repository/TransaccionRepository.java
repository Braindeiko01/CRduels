package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {
    List<Transaccion> findByUsuario_Id(String usuarioId);
}
