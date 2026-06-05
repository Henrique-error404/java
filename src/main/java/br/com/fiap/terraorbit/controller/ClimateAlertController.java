package br.com.fiap.terraorbit.controller;


import br.com.fiap.terraorbit.entity.ClimateAlert;
import br.com.fiap.terraorbit.service.ClimateAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class ClimateAlertController {

    private final ClimateAlertService service;

    @GetMapping
    public List<ClimateAlert> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ClimateAlert findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public ClimateAlert create(@RequestBody ClimateAlert alert) {
        return service.save(alert);
    }

    @PutMapping("/{id}")
    public ClimateAlert update(@PathVariable Long id,
                               @RequestBody ClimateAlert alert) {

        alert.setId(id);
        return service.save(alert);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}