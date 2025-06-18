package com.crduels.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    private UUID id = UUID.randomUUID();

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

    @Pattern(regexp = "^(https://link\\.clashroyale\\.com/invite/friend\?tag=.*)?$", message = "Enlace de amistad inválido")
    @Column(name = "link_amistad")
    private String linkAmistad;

    @DecimalMin(value = "0.0", message = "El saldo no puede ser negativo")
    private BigDecimal saldo = BigDecimal.ZERO;

    private Integer reputacion = 100;
}
