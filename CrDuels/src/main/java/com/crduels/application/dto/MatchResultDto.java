package com.crduels.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para devolver el resultado del emparejamiento de apuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultDto {
    private UUID apuesta1Id;
    private UUID apuesta2Id;
    private BigDecimal monto;
    private String modoJuego;
}
