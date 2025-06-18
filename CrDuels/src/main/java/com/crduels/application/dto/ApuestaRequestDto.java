package com.crduels.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para la creación de apuestas.
 */
@Getter
@Setter
@Builder
public class ApuestaRequestDto {
    private UUID jugador1Id;
    private UUID jugador2Id;
    private BigDecimal monto;
    private String modoJuego;
}
