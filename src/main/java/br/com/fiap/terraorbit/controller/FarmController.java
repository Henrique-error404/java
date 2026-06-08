package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.assembler.FarmAssembler;
import br.com.fiap.terraorbit.dto.request.FarmNewRequest;
import br.com.fiap.terraorbit.dto.response.FarmDTO;
import br.com.fiap.terraorbit.entity.Farm;
import br.com.fiap.terraorbit.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService service;
    private final FarmAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<FarmDTO>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<Farm> pagedAssembler,
            @RequestParam(required = false) Long userId) {

        var page = service.findAll(userId, pageable);

        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<FarmDTO> findById(@PathVariable Long id) {
        return assembler.toModel(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<FarmDTO> create(@RequestBody FarmNewRequest request) {
        return assembler.toModel(service.create(request));
    }

    @PutMapping("/{id}")
    public EntityModel<FarmDTO> update(@PathVariable Long id,
                                       @RequestBody FarmNewRequest request) {
        return assembler.toModel(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}