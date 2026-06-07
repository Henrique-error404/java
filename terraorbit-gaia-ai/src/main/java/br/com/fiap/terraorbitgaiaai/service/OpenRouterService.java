package br.com.fiap.terraorbitgaiaai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OpenRouterService {

    private final WebClient webClient;

    @Value("${openrouter.api-key}")
    private String apiKey;

    @Value("${openrouter.model}")
    private String model;

    public String analyze(
            Double temperature,
            Double humidity
    ) {

        String prompt = """
                You are Gaia AI.
                
                Analyze agricultural risk.
                
                Temperature: %s°C
                Humidity: %s%%
                
                Keep answers simple with minimal words.
                Risk level should be one of the following exact values: LOW, MEDIUM, HIGH.
                Return ONLY valid JSON.
                
                {
                  "riskLevel":"",
                  "recommendation":""
                }
                """
                .formatted(temperature, humidity);

        String body = """
                {
                  "model":"%s",
                  "messages":[
                    {
                      "role":"user",
                      "content":"%s"
                    }
                  ]
                }
                """
                .formatted(
                        model,
                        prompt.replace("\"", "\\\"")
                );

        return webClient.post()
                .uri("https://openrouter.ai/api/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}