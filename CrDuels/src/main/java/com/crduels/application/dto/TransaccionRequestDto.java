package com.crduels.application.dto;

import com.crduels.domain.model.TipoTransaccion;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransaccionRequestDto {
    private String usuarioId;
    private BigDecimal monto;
    private TipoTransaccion tipo;
}
