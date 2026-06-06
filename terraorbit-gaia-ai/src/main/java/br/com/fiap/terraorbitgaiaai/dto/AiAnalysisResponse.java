package br.com.fiap.terraorbitgaiaai.dto;

public record AiAnalysisResponse(
        String riskLevel,
        String recommendation,
        String preventiveActions
) {
}