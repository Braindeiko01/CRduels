package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.partida.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PartidaRepository extends JpaRepository<Partida, UUID> {
    Optional<Partida> findByApuesta_Id(UUID apuestaId);

}
