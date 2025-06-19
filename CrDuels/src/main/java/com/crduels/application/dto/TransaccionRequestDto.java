package com.crduels.application.dto;

import com.crduels.domain.model.TipoTransaccion;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TransaccionRequestDto {
    private UUID usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
}
