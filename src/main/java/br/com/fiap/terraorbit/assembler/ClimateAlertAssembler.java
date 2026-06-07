package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.ClimateAlertController;
import br.com.fiap.terraorbit.entity.ClimateAlert;
import org.jspecify.annotations.NullMarked;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ClimateAlertAssembler implements RepresentationModelAssembler<ClimateAlert, EntityModel<ClimateAlert>> {
    @Override
    @NullMarked
    public EntityModel<ClimateAlert> toModel(ClimateAlert entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(ClimateAlertController.class)
                        .findById(entity.getId())
                ).withSelfRel(),
                linkTo(methodOn(ClimateAlertController.class)
                        .findAll()
                ).withRel("climateAlerts")


        );

    }
}
