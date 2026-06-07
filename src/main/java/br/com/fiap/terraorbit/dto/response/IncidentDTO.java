package br.com.fiap.terraorbit.dto.response;

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
}
