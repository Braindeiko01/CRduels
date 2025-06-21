package com.crduels.domain.repository;

import com.crduels.domain.entity.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PartidaRepository extends JpaRepository<Partida, UUID> {
    Optional<Partida> findByApuesta_Id(UUID apuestaId);

    @Query("SELECT p FROM Partida p WHERE p.validada = false AND (p.apuesta.jugador1.telefono = :tel OR p.apuesta.jugador2.telefono = :tel)")
    Optional<Partida> findActivaPorTelefono(@Param("tel") String telefono);
}
