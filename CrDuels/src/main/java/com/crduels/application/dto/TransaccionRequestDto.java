package com.crduels.application.dto;

import com.crduels.domain.model.TipoTransaccion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
