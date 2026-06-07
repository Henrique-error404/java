package br.com.fiap.terraorbit.dto.response;

import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "users", itemRelation = "user")
public record UserDTO(
        Long id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {
}
