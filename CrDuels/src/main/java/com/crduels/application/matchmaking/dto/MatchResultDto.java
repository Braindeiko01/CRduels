package com.crduels.application.matchmaking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO que representa el resultado de un emparejamiento entre dos apuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MatchResultDto {
    private UUID apuesta1Id;
    private UUID apuesta2Id;
    private BigDecimal monto;
    private String modoJuego;
}
