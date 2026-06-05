package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.service.AiRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class AiRecommendationController {

    private final AiRecommendationService service;

    @GetMapping
    public List<AiRecommendation> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public AiRecommendation findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public AiRecommendation create(@RequestBody AiRecommendation recommendation) {
        return service.save(recommendation);
    }

    @PutMapping("/{id}")
    public AiRecommendation update(@PathVariable Long id,
                                   @RequestBody AiRecommendation recommendation) {

        recommendation.setId(id);
        return service.save(recommendation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}