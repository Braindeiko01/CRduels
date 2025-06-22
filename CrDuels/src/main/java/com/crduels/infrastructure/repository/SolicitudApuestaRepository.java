package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.SolicitudApuesta;
import com.crduels.domain.entity.EstadoSolicitudApuesta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SolicitudApuestaRepository extends JpaRepository<SolicitudApuesta, UUID> {

    /**
     * Obtiene todas las solicitudes que estén en el estado especificado (ej: PENDIENTE).
     */
    List<SolicitudApuesta> findByEstado(EstadoSolicitudApuesta estado);
}
