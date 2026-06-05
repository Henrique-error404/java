package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.entity.Farm;
import br.com.fiap.terraorbit.entity.User;
import br.com.fiap.terraorbit.service.FarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService service;

    @GetMapping
    public List<Farm> findAll() {
        return service.findAll();
    }
    @GetMapping("/{id}")
    public Farm findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Farm create(@RequestBody Farm farm) {
        return service.save(farm);
    }

    @PutMapping("/{id}")
    public Farm update(@PathVariable Long id,
                       @RequestBody Farm farm) {

        farm.setId(id);
        return service.save(farm);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}