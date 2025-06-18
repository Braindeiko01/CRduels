package com.crduels.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.DecimalMin;
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

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100)
    private String nombre;

    @Email(message = "Correo inválido")
    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+?\d{7,15}$", message = "Número de teléfono inválido")
    @Column(unique = true)
    private String telefono;

    @NotBlank
    @Pattern(regexp = "^#?[A-Z0-9]{5,12}$", message = "Tag inválido")
    @Column(name = "tag_clash", unique = true)
    private String tagClash;

    @Pattern(
        regexp = "^(https://link\\.clashroyale\\.com/invite/friend\\?tag=[A-Z0-9]+)?$",
        message = "Enlace de amistad inválido"
    )
    @Column(name = "link_amistad")
    private String linkAmistad;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private BigDecimal saldo = BigDecimal.ZERO;

    private Integer reputacion = 100;

}
