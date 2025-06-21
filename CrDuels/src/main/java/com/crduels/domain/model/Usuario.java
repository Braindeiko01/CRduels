package com.crduels.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @Column(name = "id", unique = true)
    private String id;

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
