package br.com.fiap.terraorbit.dto.response;

import br.com.fiap.terraorbit.entity.Farm;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "recommendations", itemRelation = "link")
public record FarmDTO(
        Long id,
        String farmName,
        String location,
        Double farmSizeHectares,
        Long ownerId,
        LocalDateTime createAt
) {

    public static FarmDTO fromEntity(Farm entity) {
        return new FarmDTO(
                entity.getId(),
                entity.getFarmName(),
                entity.getLocation(),
                entity.getFarmSizeHectares().doubleValue(),
                entity.getOwner().getId(),
                entity.getCreatedAt()
        );
    }
}
