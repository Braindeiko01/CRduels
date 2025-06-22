package com.crduels.application.service;

import com.crduels.domain.entity.Apuesta;
import com.crduels.domain.entity.EstadoApuesta;
import com.crduels.domain.entity.EstadoSolicitudApuesta;
import com.crduels.domain.entity.SolicitudApuesta;
import com.crduels.infrastructure.dto.rs.MatchResultDto;
import com.crduels.infrastructure.dto.rq.ApuestaRequest;
import com.crduels.infrastructure.dto.rs.ApuestaResponse;
import com.crduels.infrastructure.repository.SolicitudApuestaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchmakingService {

    private final SolicitudApuestaRepository solicitudRepository;
    private final ApuestaService apuestaService;
    private final MatchSseService matchSseService;


    /**
     * Ejecuta el proceso de matchmaking emparejando solicitudes de apuesta con
     * el mismo monto y modo de juego. Por cada par de solicitudes se crea una
     * {@link Apuesta} y se retorna un {@link MatchResultDto} con la información
     * relevante del emparejamiento.
     */
    @Transactional
    public List<MatchResultDto> ejecutarMatchmaking() {
        List<SolicitudApuesta> pendientes = solicitudRepository.findByEstado(EstadoSolicitudApuesta.PENDIENTE);

        // Agrupar por monto y modo de juego
        Map<Key, List<SolicitudApuesta>> grupos = pendientes.stream()
                .sorted(Comparator.comparing(SolicitudApuesta::getCreadoEn))
                .collect(Collectors.groupingBy(s -> new Key(s.getMonto(), s.getModoJuego())));

        List<MatchResultDto> resultados = new ArrayList<>();

        for (List<SolicitudApuesta> grupo : grupos.values()) {
            for (int i = 0; i + 1 < grupo.size(); i += 2) {
                SolicitudApuesta s1 = grupo.get(i);
                SolicitudApuesta s2 = grupo.get(i + 1);

                ApuestaRequest rq = ApuestaRequest.builder()
                        .jugador1Id(s1.getJugador().getId())
                        .jugador2Id(s2.getJugador().getId())
                        .monto(s1.getMonto())
                        .modoJuego(s1.getModoJuego())
                        .build();

                ApuestaResponse apuesta = apuestaService.crearApuesta(rq);
                apuestaService.cambiarEstado(apuesta.getId(), EstadoApuesta.EMPAREJADA);

                s1.setEstado(EstadoSolicitudApuesta.EMPAREJADA);
                s2.setEstado(EstadoSolicitudApuesta.EMPAREJADA);
                solicitudRepository.saveAll(Arrays.asList(s1, s2));

                resultados.add(MatchResultDto.builder()
                        .apuestaId(apuesta.getId())
                        .jugador1Id(toUuid(s1.getJugador().getId()))
                        .jugador2Id(toUuid(s2.getJugador().getId()))
                        .monto(apuesta.getMonto())
                        .modoJuego(apuesta.getModoJuego())
                        .build());

                matchSseService.notifyMatch(apuesta.getId(), s1.getJugador(), s2.getJugador());

            }
        }

        return resultados;
    }

    private UUID toUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            return UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8));
        }
    }

    /**
     * Llave para el agrupamiento por monto y modo de juego.
     */
    private record Key(BigDecimal monto, String modo) {
    }
}
