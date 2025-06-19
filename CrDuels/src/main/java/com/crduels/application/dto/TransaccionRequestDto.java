package com.crduels.application.dto;

import com.crduels.domain.model.TipoTransaccion;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequestDto {
    private UUID usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
}
