package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.DueloRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DueloRequestRepository extends JpaRepository<DueloRequest, UUID> {

    List<DueloRequest> findByModoJuegoAndEstado(String modoJuego, String estado);

    Optional<DueloRequest> findByUsuarioIdAndEstado(String usuarioId, String estado);
}
