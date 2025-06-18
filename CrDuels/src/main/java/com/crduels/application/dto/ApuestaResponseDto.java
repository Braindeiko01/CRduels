package com.crduels.application.dto;

import com.crduels.domain.model.EstadoApuesta;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para responder con la información de una apuesta.
 */
@Getter
@Setter
@Builder
public class ApuestaResponseDto {
    private UUID id;
    private BigDecimal monto;
    private String modoJuego;
    private EstadoApuesta estado;
    private LocalDateTime creadoEn;
}
