package com.crduels.application.service;

import com.crduels.infrastructure.dto.rq.MatchmakingJoinRequest;
import com.crduels.infrastructure.dto.rs.MatchmakingPartidaDto;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class RealtimeMatchmakingService {

    private static class Player {
        final String usuarioId;
        final String modoJuego;

        Player(String usuarioId, String modoJuego) {
            this.usuarioId = usuarioId;
            this.modoJuego = modoJuego;
        }
    }

    private final Queue<Player> waitingQueue = new ConcurrentLinkedQueue<>();
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    /**
     * Register a player into the matchmaking queue.
     * @return true if a match was made immediately, false if player must wait
     */
    public synchronized boolean joinQueue(MatchmakingJoinRequest request) {
        if (waitingQueue.stream().anyMatch(p -> p.usuarioId.equals(request.getUsuarioId()))) {
            return false; // already waiting
        }
        Player opponent = waitingQueue.poll();
        if (opponent == null) {
            waitingQueue.add(new Player(request.getUsuarioId(), request.getModoJuego()));
            return false;
        }
        Player current = new Player(request.getUsuarioId(), request.getModoJuego());
        emparejarJugadores(current, opponent);
        return true;
    }

    public SseEmitter subscribe(String usuarioId) {
        SseEmitter emitter = new SseEmitter(30_000L);
        emitters.put(usuarioId, emitter);
        emitter.onCompletion(() -> emitters.remove(usuarioId));
        emitter.onError(e -> emitters.remove(usuarioId));
        emitter.onTimeout(() -> {
            sendTimeout(usuarioId);
            emitters.remove(usuarioId);
        });
        return emitter;
    }

    public void cancelar(String usuarioId) {
        waitingQueue.removeIf(p -> p.usuarioId.equals(usuarioId));
        SseEmitter emitter = emitters.remove(usuarioId);
        if (emitter != null) {
            emitter.complete();
        }
    }

    private void sendTimeout(String usuarioId) {
        SseEmitter emitter = emitters.get(usuarioId);
        if (emitter == null) {
            return;
        }
        try {
            emitter.send(SseEmitter.event().name("timeout").data(Map.of("timeout", true)));
            emitter.complete();
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }

    private void emparejarJugadores(Player p1, Player p2) {
        MatchmakingPartidaDto partida = MatchmakingPartidaDto.builder()
                .partidaId(UUID.randomUUID())
                .jugador1Id(p1.usuarioId)
                .jugador2Id(p2.usuarioId)
                .modoJuego(p1.modoJuego)
                .build();
        sendMatch(p1.usuarioId, partida);
        sendMatch(p2.usuarioId, partida);
    }

    private void sendMatch(String usuarioId, MatchmakingPartidaDto partida) {
        SseEmitter emitter = emitters.remove(usuarioId);
        if (emitter == null) {
            return;
        }
        try {
            emitter.send(SseEmitter.event().name("match").data(partida));
            emitter.complete();
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
}
