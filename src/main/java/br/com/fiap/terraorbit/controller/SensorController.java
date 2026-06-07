package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.assembler.SensorAssembler;
import br.com.fiap.terraorbit.dto.request.SensorNewRequest;
import br.com.fiap.terraorbit.dto.response.SensorDTO;
import br.com.fiap.terraorbit.entity.Sensor;
import br.com.fiap.terraorbit.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService service;
    private final SensorAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<SensorDTO>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<Sensor> pagedAssembler,
            @RequestParam(required = false) Long farmId) {

        var page = service.findAll(farmId, pageable);

        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<SensorDTO> findById(@PathVariable Long id) {
        return assembler.toModel(
                service.findById(id)
        );
    }

    @PostMapping
    public EntityModel<SensorDTO> create(@RequestBody SensorNewRequest request) {
        return assembler.toModel(service.create(request));
    }

    @PutMapping("/{id}")
    public EntityModel<SensorDTO> update(@PathVariable Long id,
                                         @RequestBody SensorNewRequest request) {
        return assembler.toModel(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}