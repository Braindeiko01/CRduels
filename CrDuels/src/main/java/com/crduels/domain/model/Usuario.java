package com.crduels.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    private String nombre;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telefono;

    @Column(name = "tag_clash", unique = true)
    private String tagClash;

    @Column(name = "link_amistad")
    private String linkAmistad;

    private BigDecimal saldo = BigDecimal.ZERO;

    private Integer reputacion = 100;

}
