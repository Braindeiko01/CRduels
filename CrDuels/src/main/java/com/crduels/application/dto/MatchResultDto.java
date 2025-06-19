package com.crduels.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para representar el resultado de un duelo.
 */
@Data
@AllArgsConstructor
public class MatchResultDto {
    private UUID apuesta1Id;
    private UUID apuesta2Id;
    private BigDecimal monto;
    private String modoJuego;
}
