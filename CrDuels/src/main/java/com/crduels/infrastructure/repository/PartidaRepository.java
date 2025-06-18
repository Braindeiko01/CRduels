package com.crduels.infrastructure.repository;

import com.crduels.domain.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PartidaRepository extends JpaRepository<Partida, UUID> {
    Optional<Partida> findByApuestaId(UUID apuestaId);
}
