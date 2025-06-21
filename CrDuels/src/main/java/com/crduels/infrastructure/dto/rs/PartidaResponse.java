package com.crduels.infrastructure.dto.rs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para responder con la información de una partida.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidaResponse {
    private UUID id;
    private UUID apuestaId;
    private String ganadorId;
    private boolean validada;
    private LocalDateTime validadaEn;
}
