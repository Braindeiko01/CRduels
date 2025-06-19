package com.crduels.application.dto;

import com.crduels.domain.model.EstadoTransaccion;
import com.crduels.domain.model.TipoTransaccion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TransaccionResponseDto {
    private UUID id;
    private UUID usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
    private EstadoTransaccion estado;
    private LocalDateTime creadoEn;
}
