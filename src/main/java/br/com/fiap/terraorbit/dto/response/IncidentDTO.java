package br.com.fiap.terraorbit.dto.response;

import br.com.fiap.terraorbit.entity.Incident;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "incidents", itemRelation = "incident")
public record IncidentDTO(
        Long id,
        String incidentType,
        String incidentDescription,
        LocalDateTime incidentDate,
        String incidentStatus,
        Long farmId
) {
    public static IncidentDTO fromEntity(Incident entity) {
        return new IncidentDTO(
                entity.getId(),
                entity.getIncidentType(),
                entity.getIncidentDescription(),
                entity.getIncidentDate(),
                entity.getIncidentStatus(),
                entity.getFarm().getId()
        );
    }
}
