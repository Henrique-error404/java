package br.com.fiap.terraorbit.dto;

import br.com.fiap.terraorbit.entity.RISKLEVEL;

public record AiRecommendationDTO(
        Long id,
        String recommendation,
        RISKLEVEL risklevel,
        Long farmId
) {
}
