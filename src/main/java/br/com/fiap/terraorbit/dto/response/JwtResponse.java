package br.com.fiap.terraorbit.dto.response;

public record JwtResponse(
        String token,
        Long userId
) {
}
