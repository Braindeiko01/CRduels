package com.crduels.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa una apuesta realizada entre dos jugadores.
 */

@Entity
@Table(name = "apuestas")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador1_id", nullable = false)
    private Usuario jugador1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador2_id", nullable = false)
    private Usuario jugador2;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(name = "modo_juego", nullable = false)
    private String modoJuego;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoApuesta estado;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;
}
