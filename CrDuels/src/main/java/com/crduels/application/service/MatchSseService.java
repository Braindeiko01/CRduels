package com.crduels.application.service;

import com.crduels.domain.entity.Jugador;
import com.crduels.domain.entity.partida.Partida;
import com.crduels.infrastructure.dto.rs.MatchSseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchSseService {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String jugadorId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.put(jugadorId, emitter);
        emitter.onCompletion(() -> emitters.remove(jugadorId));
        emitter.onTimeout(() -> emitters.remove(jugadorId));
        emitter.onError(ex -> emitters.remove(jugadorId));
        return emitter;
    }

    public void notifyMatch(UUID apuestaId, Jugador jugador1, Jugador jugador2) {
        sendEvent(jugador1.getId(), apuestaId, jugador2);
        sendEvent(jugador2.getId(), apuestaId, jugador1);
    }

    public void notifyMatch(Partida partida) {
        sendMatch(partida.getJugador1().getId(), partida);
        sendMatch(partida.getJugador2().getId(), partida);
    }

    private void sendEvent(String receptorId, UUID apuestaId, Jugador oponente) {
        SseEmitter emitter = emitters.get(receptorId);
        if (emitter == null) {
            return;
        }
        MatchSseDto dto = MatchSseDto.builder()
                .apuestaId(apuestaId)
                .jugadorOponenteId(oponente.getId())
                .jugadorOponenteTag(oponente.getTagClash())
                .build();
        try {
            emitter.send(SseEmitter.event()
                    .name("match-found")
                    .data(dto));
        } catch (IOException e) {
            emitters.remove(receptorId);
            emitter.completeWithError(e);
        }
    }

    private void sendMatch(String receptorId, Partida partida) {
        SseEmitter emitter = emitters.get(receptorId);
        if (emitter == null) {
            return;
        }
        Map<String, Object> partidaMap = new HashMap<>();
        partidaMap.put("id", partida.getId());
        Map<String, Object> payload = new HashMap<>();
        payload.put("match", true);
        payload.put("partida", partidaMap);
        try {
            emitter.send(SseEmitter.event().data(payload));
        } catch (IOException e) {
            emitters.remove(receptorId);
            emitter.completeWithError(e);
        }
    }
}
