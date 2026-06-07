package br.com.fiap.terraorbit.dto.request;

public record ClimateRequest(
        Double temperature,
        Double humidity
) {
}
