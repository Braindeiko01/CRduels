package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransaccionRepository extends JpaRepository<Transaccion, UUID> {
    List<Transaccion> findByJugador_Id(String jugadorId);
}
