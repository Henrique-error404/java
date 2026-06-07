package br.com.fiap.terraorbit.client;

import br.com.fiap.terraorbit.dto.request.ClimateRequest;
import br.com.fiap.terraorbit.dto.response.AiAnalysisResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GaiaAiClient {

    private final WebClient webClient;

    public AiAnalysisResponse analyze(
            Double temperature,
            Double humidity
    ) {

        ClimateRequest request =
                new ClimateRequest(
                        temperature,
                        humidity
                );

        return webClient.post()
                .uri("http://localhost:8081/ai/analyze")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AiAnalysisResponse.class)
                .block();
    }
}