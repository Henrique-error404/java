package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.FarmController;
import br.com.fiap.terraorbit.controller.SensorController;
import br.com.fiap.terraorbit.dto.response.SensorDTO;
import br.com.fiap.terraorbit.entity.Sensor;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SensorAssembler implements RepresentationModelAssembler<Sensor, EntityModel<SensorDTO>> {
    @Override
    @NullMarked
    public EntityModel<SensorDTO> toModel(Sensor entity) {
        return EntityModel.of(SensorDTO.fromEntity(entity),
                linkTo(methodOn(SensorController.class)
                        .findById(entity.getId())
                ).withSelfRel(),
                linkTo(methodOn(SensorController.class)
                        .findAll(Pageable.unpaged(), null, null)
                ).withRel("all-sensors"),
                linkTo(methodOn(FarmController.class)
                        .findById(entity.getFarm().getId())
                ).withRel("farm")
        );
    }
}