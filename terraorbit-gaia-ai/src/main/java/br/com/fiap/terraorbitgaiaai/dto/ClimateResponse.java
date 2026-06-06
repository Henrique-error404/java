package br.com.fiap.terraorbitgaiaai.dto;


public record ClimateResponse(
        String riskLevel,
        String recommendation,
        String preventiveActions
) {
}