package br.com.fiap.terraorbitgaiaai.controller;

import br.com.fiap.terraorbitgaiaai.dto.ClimateRequest;
import br.com.fiap.terraorbitgaiaai.service.OpenRouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {

    private final OpenRouterService service;

    @PostMapping("/analyze")
    public String analyze(
            @RequestBody ClimateRequest request
    ) {

        var response = service.analyze(
                request.temperature(),
                request.humidity()
        );

        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response);

        return root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asString();
    }
}
