package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.AiRecommendationController;
import br.com.fiap.terraorbit.entity.AiRecommendation;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AIRecommendationAssembler implements RepresentationModelAssembler<AiRecommendation, EntityModel<AiRecommendation>> {

    @Override
    public EntityModel<AiRecommendation> toModel(AiRecommendation r) {
        return EntityModel.of(
                r,
                linkTo(methodOn(AiRecommendationController.class)
                        .findById(r.getId())
                ).withSelfRel()

        );
    }
}
