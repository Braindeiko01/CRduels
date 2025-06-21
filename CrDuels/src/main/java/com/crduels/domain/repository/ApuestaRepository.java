package com.crduels.domain.repository;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ApuestaRepository extends JpaRepository<Apuesta, UUID> {
    List<Apuesta> findByEstado(EstadoApuesta estado);
}
