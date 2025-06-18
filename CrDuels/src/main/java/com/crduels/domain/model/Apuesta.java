package com.crduels.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Column(name = "jugador1_id", columnDefinition = "uuid", nullable = false)
    private UUID jugador1Id;

    @Column(name = "jugador2_id", columnDefinition = "uuid", nullable = false)
    private UUID jugador2Id;

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
