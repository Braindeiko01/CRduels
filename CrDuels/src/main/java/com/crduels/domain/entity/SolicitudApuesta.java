package com.crduels.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "solicitudes_apuesta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudApuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id", nullable = false)
    private Usuario jugador;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(name = "modo_juego", nullable = false)
    private String modoJuego;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitudApuesta estado;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;
}
