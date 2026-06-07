package br.com.fiap.terraorbit.dto.response;

import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.entity.RISKLEVEL;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "recommendations", itemRelation = "recommendation")
public record AiRecommendationDTO(
        Long id,
        String recommendation,
        RISKLEVEL risklevel,
        Long farmId,
        LocalDateTime generatedAt
) {

    public static AiRecommendationDTO fromEntity(AiRecommendation entity) {
        return new AiRecommendationDTO(
                entity.getId(),
                entity.getRecommendation(),
                entity.getRiskLevel(),
                entity.getFarm().getId(),
                entity.getGeneratedAt()
        );
    }

}
