package br.com.fiap.terraorbit.controller;


import br.com.fiap.terraorbit.assembler.ClimateAlertAssembler;
import br.com.fiap.terraorbit.dto.response.ClimateAlertDTO;
import br.com.fiap.terraorbit.entity.ClimateAlert;
import br.com.fiap.terraorbit.service.ClimateAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class ClimateAlertController {

    private final ClimateAlertService service;
    private final ClimateAlertAssembler assembler;

    @GetMapping
    public PagedModel<EntityModel<ClimateAlertDTO>> findAll(
            Pageable pageable,
            PagedResourcesAssembler<ClimateAlert> pagedAssembler,
            @RequestParam(required = false) Long farmId) {

        var page = service.findAll(farmId, pageable);

        return pagedAssembler.toModel(page, assembler);
    }

    @GetMapping("/{id}")
    public EntityModel<ClimateAlertDTO> findById(@PathVariable Long id) {
        return assembler.toModel(
                service.findById(id)
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}