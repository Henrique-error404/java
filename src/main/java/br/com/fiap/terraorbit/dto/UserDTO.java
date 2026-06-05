package br.com.fiap.terraorbit.dto;

import java.time.LocalDateTime;

public record UserDTO(
        Long id,
        String name,
        String email,
        String role,
        LocalDateTime createdAt
) {
}
