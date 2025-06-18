package com.crduels.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO para la creación de partidas.
 */
@Getter
@Setter
@Builder
public class PartidaRequestDto {
    private UUID apuestaId;
    private UUID ganadorId;
    private String resultadoJson;
}
