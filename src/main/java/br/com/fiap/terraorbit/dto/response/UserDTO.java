package br.com.fiap.terraorbit.dto.response;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {
}
