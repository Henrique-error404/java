package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.assembler.UserAssembler;
import br.com.fiap.terraorbit.dto.request.UserNewRequest;
import br.com.fiap.terraorbit.dto.response.UserDTO;
import br.com.fiap.terraorbit.entity.User;
import br.com.fiap.terraorbit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<UserDTO>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<User> pagedAssembler) {

        var page = service.findAll(pageable);

        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<UserDTO> findById(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PutMapping("/{id}")
    public EntityModel<UserDTO> update(@PathVariable Long id,
                                       @RequestBody UserNewRequest request) {

        return assembler.toModel(service.update(id, request));
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}