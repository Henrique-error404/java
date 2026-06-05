package br.com.fiap.terraorbit.dto;

import java.time.LocalDateTime;

public record IncidentDTO(
        Long id,
        String incidentType,
        String incidentDescription,
        LocalDateTime incidentDate,
        String incidentStatus,
        Long farmId
) {
}
