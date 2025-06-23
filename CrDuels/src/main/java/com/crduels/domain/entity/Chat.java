package com.crduels.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    private UUID id;

    private UUID partidaId;

    @ElementCollection
    @CollectionTable(name = "chat_usuarios", joinColumns = @JoinColumn(name = "chat_id"))
    @Column(name = "usuario_id")
    private List<String> usuarios;
}
