package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.AiRecommendationController;
import br.com.fiap.terraorbit.controller.FarmController;
import br.com.fiap.terraorbit.dto.response.AiRecommendationDTO;
import br.com.fiap.terraorbit.entity.AiRecommendation;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AIRecommendationAssembler implements RepresentationModelAssembler<AiRecommendation, EntityModel<AiRecommendationDTO>> {

    @Override
    @NullMarked
    public EntityModel<AiRecommendationDTO> toModel(AiRecommendation entity) {
        return EntityModel.of(
                AiRecommendationDTO.fromEntity(entity),
                linkTo(methodOn(AiRecommendationController.class)
                        .findById(entity.getId())
                ).withSelfRel(),

                linkTo(methodOn(AiRecommendationController.class)
                        .findAll(Pageable.unpaged(), null)
                ).withRel("all-recommendations"),

                linkTo(methodOn(FarmController.class)
                        .findById(entity.getFarm().getId())
                ).withRel("farm")
        );
    }
}
