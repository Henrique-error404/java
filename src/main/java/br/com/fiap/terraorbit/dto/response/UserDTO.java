package br.com.fiap.terraorbit.dto.response;

import br.com.fiap.terraorbit.entity.User;
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
    public static UserDTO fromEntity(User entity) {
        return new UserDTO(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getUserRole(),
                entity.getCreatedAt()
        );
    }
}
