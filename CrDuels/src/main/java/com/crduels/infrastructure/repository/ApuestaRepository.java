package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.EstadoApuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApuestaRepository extends JpaRepository<Apuesta, UUID> {
    List<Apuesta> findByEstado(EstadoApuesta estado);
}
