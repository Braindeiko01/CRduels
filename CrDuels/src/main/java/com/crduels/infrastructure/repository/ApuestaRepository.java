package com.crduels.infrastructure.repository;

import com.crduels.domain.entity.Apuesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApuestaRepository extends JpaRepository<Apuesta, UUID> {
}
