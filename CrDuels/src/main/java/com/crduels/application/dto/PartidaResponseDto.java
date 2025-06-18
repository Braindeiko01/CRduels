package com.crduels.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para responder con la información de una partida.
 */
@Getter
@Setter
@Builder
public class PartidaResponseDto {
    private UUID id;
    private UUID apuestaId;
    private UUID ganadorId;
    private boolean validada;
    private LocalDateTime validadaEn;
}
