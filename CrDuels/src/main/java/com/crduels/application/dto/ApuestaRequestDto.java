package com.crduels.application.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO para la creación de apuestas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApuestaRequestDto {
    private UUID jugador1Id;
    private UUID jugador2Id;
    private BigDecimal monto;
    private String modoJuego;
}
