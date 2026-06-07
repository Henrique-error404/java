package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.assembler.AIRecommendationAssembler;
import br.com.fiap.terraorbit.dto.response.AiRecommendationDTO;
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
    public PagedModel<EntityModel<AiRecommendationDTO>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<AiRecommendation> pagedAssembler,
            @RequestParam(required = false) Long farmId) {

        var page = service.findAll(farmId, pageable);

        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<AiRecommendationDTO> findById(@PathVariable Long id) {
        return assembler.toModel(
                service.findById(id)
        );
    }

    @PostMapping("/generate/{farmId}")
    public EntityModel<AiRecommendationDTO> generateRecommendation(@PathVariable Long farmId) {
        return assembler.toModel(
                service.analyze(farmId)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}