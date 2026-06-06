package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.assembler.AIRecommendationAssembler;
import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.service.AiRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class AiRecommendationController {

    private final AiRecommendationService service;
    private final AIRecommendationAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<AiRecommendation>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<AiRecommendation> pagedAssembler) {

        var page = service.findAll(pageable);

        return pagedAssembler.toModel(page, assembler);
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