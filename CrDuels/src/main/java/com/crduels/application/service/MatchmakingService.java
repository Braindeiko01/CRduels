package com.crduels.application.service;

import com.crduels.domain.entity.DueloRequest;
import com.crduels.domain.entity.Partida;
import com.crduels.infrastructure.repository.DueloRequestRepository;
import com.crduels.infrastructure.repository.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MatchmakingService {

    private final DueloRequestRepository dueloRequestRepository;
    private final PartidaRepository partidaRepository;
    private final ChatService chatService;

    public DueloRequest registrarSolicitudDuelo(String usuarioId, String modoJuego) {
        DueloRequest solicitud = DueloRequest.builder()
                .id(UUID.randomUUID())
                .usuarioId(usuarioId)
                .modoJuego(modoJuego)
                .estado("PENDIENTE")
                .timestamp(LocalDateTime.now())
                .build();

        return dueloRequestRepository.save(solicitud);
    }

    public Optional<Partida> intentarEmparejar(String usuarioId, String modoJuego) {
        List<DueloRequest> pendientes = dueloRequestRepository
                .findByModoJuegoAndEstado(modoJuego, "PENDIENTE");

        for (DueloRequest otro : pendientes) {
            if (!otro.getUsuarioId().equals(usuarioId)) {
                // Actualizar solicitudes
                otro.setEstado("EMPAREJADO");
                dueloRequestRepository.save(otro);

                DueloRequest actual = dueloRequestRepository
                        .findByUsuarioIdAndEstado(usuarioId, "PENDIENTE")
                        .orElse(null);

                if (actual != null) {
                    actual.setEstado("EMPAREJADO");
                    dueloRequestRepository.save(actual);

                    return Optional.of(crearPartida(actual.getUsuarioId(), otro.getUsuarioId(), modoJuego));
                }
            }
        }

        return Optional.empty();
    }

    private Partida crearPartida(String jugador1Id, String jugador2Id, String modoJuego) {
        UUID partidaId = UUID.randomUUID();

        UUID chatId = chatService.crearChatParaPartida(partidaId, jugador1Id, jugador2Id);

        Partida partida = Partida.builder()
                .id(partidaId)
                .jugador1Id(jugador1Id)
                .jugador2Id(jugador2Id)
                .modoJuego(modoJuego)
                .estado("ACTIVA")
                .creada(LocalDateTime.now())
                .chatId(chatId)
                .build();

        return partidaRepository.save(partida);
    }
}
