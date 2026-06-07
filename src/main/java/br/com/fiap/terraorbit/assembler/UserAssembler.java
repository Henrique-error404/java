package br.com.fiap.terraorbit.assembler;

import br.com.fiap.terraorbit.controller.FarmController;
import br.com.fiap.terraorbit.controller.UserController;
import br.com.fiap.terraorbit.dto.response.UserDTO;
import br.com.fiap.terraorbit.entity.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<UserDTO>> {
    @Override
    @NullMarked
    public EntityModel<UserDTO> toModel(User entity) {
        return EntityModel.of(UserDTO.fromEntity(entity),
                linkTo(methodOn(UserController.class)
                        .findById(entity.getId())
                ).withSelfRel(),
                linkTo(methodOn(UserController.class)
                        .findAll(Pageable.unpaged(), null)
                ).withRel("all-users"),
                linkTo(methodOn(FarmController.class)
                        .findAll(Pageable.unpaged(), null, entity.getId())
                ).withRel("farms")
        );
    }
}