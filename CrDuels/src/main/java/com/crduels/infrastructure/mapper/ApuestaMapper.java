package com.crduels.infrastructure.mapper;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rs.ApuestaResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public final class ApuestaMapper {

    private ApuestaMapper() {
    }

    private static final BigDecimal PORCENTAJE_COMISION = BigDecimal.valueOf(20);

    public static Apuesta toEntity(ApuestaRequest dto) {
        BigDecimal monto = dto.getMonto();
        BigDecimal bote = monto.multiply(BigDecimal.valueOf(2));
        BigDecimal comision = calcularComision(monto);
        BigDecimal premio = bote.subtract(comision);

        return Apuesta.builder()
                .monto(monto)
                .comision(comision)
                .premio(premio)
                .estado(EstadoApuesta.PENDIENTE)
                .creadoEn(LocalDateTime.now())
                .build();
    }

    private static BigDecimal calcularComision(BigDecimal bote) {
        return bote.multiply(PORCENTAJE_COMISION)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public static ApuestaResponse toDto(Apuesta entity) {
        return ApuestaResponse.builder()
                .id(entity.getId())
                .monto(entity.getMonto())
                .premio(entity.getPremio())
                .estado(entity.getEstado())
                .build();
    }

}
