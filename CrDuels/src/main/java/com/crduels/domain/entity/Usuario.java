package com.crduels.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
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

    public Usuario() {}

    // Getters y setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTagClash() {
        return tagClash;
    }

    public void setTagClash(String tagClash) {
        this.tagClash = tagClash;
    }

    public String getLinkAmistad() {
        return linkAmistad;
    }

    public void setLinkAmistad(String linkAmistad) {
        this.linkAmistad = linkAmistad;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Integer getReputacion() {
        return reputacion;
    }

    public void setReputacion(Integer reputacion) {
        this.reputacion = reputacion;
    }
}
