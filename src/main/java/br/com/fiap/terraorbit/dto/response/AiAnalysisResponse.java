package br.com.fiap.terraorbit.dto.response;

public record AiAnalysisResponse(
        String riskLevel,
        String recommendation
) {
}