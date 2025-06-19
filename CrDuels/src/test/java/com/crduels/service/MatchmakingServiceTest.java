package com.crduels.service;

import com.crduels.application.dto.MatchResultDto;
import com.crduels.application.service.MatchmakingService;
import com.crduels.domain.model.Apuesta;
import com.crduels.domain.model.EstadoApuesta;
import com.crduels.infrastructure.repository.ApuestaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(MatchmakingService.class)
class MatchmakingServiceTest {

    @Autowired
    private MatchmakingService matchmakingService;

    @Autowired
    private ApuestaRepository apuestaRepository;

    private Apuesta crearApuesta(BigDecimal monto, String modo) {
        return Apuesta.builder()
                .jugador1Id(UUID.randomUUID())
                .jugador2Id(UUID.randomUUID())
                .monto(monto)
                .modoJuego(modo)
                .estado(EstadoApuesta.PENDIENTE)
                .creadoEn(LocalDateTime.now())
                .build();
    }

    @Test
    void ejecutarMatchmaking_emparejaPendientes() {
        Apuesta a1 = crearApuesta(new BigDecimal("5"), "1v1");
        Apuesta a2 = crearApuesta(new BigDecimal("5"), "1v1");
        apuestaRepository.save(a1);
        apuestaRepository.save(a2);
        apuestaRepository.flush();

        List<MatchResultDto> resultados = matchmakingService.ejecutarMatchmaking();
        apuestaRepository.flush();

        assertEquals(1, resultados.size());
        assertEquals(EstadoApuesta.EMPAREJADA, apuestaRepository.findById(a1.getId()).get().getEstado());
        assertEquals(EstadoApuesta.EMPAREJADA, apuestaRepository.findById(a2.getId()).get().getEstado());
        MatchResultDto r = resultados.get(0);
        assertEquals(a1.getId(), r.getApuesta1Id());
        assertEquals(a2.getId(), r.getApuesta2Id());
        assertEquals(new BigDecimal("5"), r.getMonto());
        assertEquals("1v1", r.getModoJuego());
    }
}
