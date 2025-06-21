package com.crduels.application.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para responder con la información de una partida.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaResponseDto {
    private UUID id;
    private UUID apuestaId;
    private String ganadorId;
    private boolean validada;
    private LocalDateTime validadaEn;
}
