package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.assembler.IncidentAssembler;
import br.com.fiap.terraorbit.dto.request.IncidentNewRequest;
import br.com.fiap.terraorbit.dto.response.IncidentDTO;
import br.com.fiap.terraorbit.entity.Incident;
import br.com.fiap.terraorbit.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService service;
    private final IncidentAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<IncidentDTO>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<Incident> pagedAssembler,
            @RequestParam(required = false) Long farmId) {

        var page = service.findAll(farmId, pageable);

        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<IncidentDTO> findById(@PathVariable Long id) {
        return assembler.toModel(
                service.findById(id)
        );
    }

    @PostMapping
    public EntityModel<IncidentDTO> create(@RequestBody IncidentNewRequest request) {
        return assembler.toModel(service.create(request));
    }

    @PutMapping("/{id}")
    public EntityModel<IncidentDTO> update(@PathVariable Long id,
                                           @RequestBody IncidentNewRequest request) {
        return assembler.toModel(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}