package com.crduels.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "duelo_request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DueloRequest {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String usuarioId;

    @Column(nullable = false)
    private String modoJuego;

    @Column(nullable = false)
    private String estado; // PENDIENTE, EMPAREJADO

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
