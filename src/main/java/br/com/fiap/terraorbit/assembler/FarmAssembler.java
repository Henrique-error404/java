package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.AiRecommendationController;
import br.com.fiap.terraorbit.controller.ClimateAlertController;
import br.com.fiap.terraorbit.controller.FarmController;
import br.com.fiap.terraorbit.controller.UserController;
import br.com.fiap.terraorbit.dto.response.FarmDTO;
import br.com.fiap.terraorbit.entity.Farm;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FarmAssembler implements RepresentationModelAssembler<Farm, EntityModel<FarmDTO>> {
    @Override
    @NullMarked
    public EntityModel<FarmDTO> toModel(Farm entity) {
        return EntityModel.of(FarmDTO.fromEntity(entity),
                linkTo(methodOn(FarmController.class)
                        .findById(entity.getId())
                ).withSelfRel(),
                linkTo(methodOn(FarmController.class)
                        .findAll(Pageable.unpaged(), null, null)
                ).withRel("all-farms"),
                linkTo(methodOn(UserController.class)
                        .findById(entity.getOwner().getId())
                ).withRel("user"),
                linkTo(methodOn(ClimateAlertController.class)
                        .findAll(Pageable.unpaged(), null, entity.getId())
                ).withRel("alerts"),
                linkTo(methodOn(AiRecommendationController.class)
                        .findAll(Pageable.unpaged(), null, entity.getId())
                ).withRel("recommendations")
        );

    }
}