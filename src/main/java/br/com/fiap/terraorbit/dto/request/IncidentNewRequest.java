package br.com.fiap.terraorbit.dto.request;

public record IncidentNewRequest(
        Long id,
        String incidentType,
        String incidentDescription,
        String incidentStatus,
        Long farmId
) {
}
