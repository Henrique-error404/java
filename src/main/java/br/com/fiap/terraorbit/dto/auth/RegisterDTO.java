package br.com.fiap.terraorbit.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDTO(
        @NotNull
        String name,
        @Email
        @NotNull
        String email,
        @NotNull
        @Size(min = 3)
        String password
) {
}
