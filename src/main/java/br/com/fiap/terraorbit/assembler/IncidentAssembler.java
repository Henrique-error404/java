package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.FarmController;
import br.com.fiap.terraorbit.controller.IncidentController;
import br.com.fiap.terraorbit.dto.response.IncidentDTO;
import br.com.fiap.terraorbit.entity.Incident;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class IncidentAssembler implements RepresentationModelAssembler<Incident, EntityModel<IncidentDTO>> {
    @Override
    @NullMarked
    public EntityModel<IncidentDTO> toModel(Incident entity) {
        return EntityModel.of(IncidentDTO.fromEntity(entity),
                linkTo(methodOn(IncidentController.class)
                        .findById(entity.getId())
                ).withSelfRel(),
                linkTo(methodOn(IncidentController.class)
                        .findAll(Pageable.unpaged(), null, null)
                ).withRel("all-incidents"),
                linkTo(methodOn(FarmController.class)
                        .findById(entity.getFarm().getId())
                ).withRel("user")
        );
    }
}