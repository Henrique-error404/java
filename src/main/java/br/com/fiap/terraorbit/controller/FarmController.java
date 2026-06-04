package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.entity.Farm;
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

    @PostMapping
    public Farm create(@RequestBody Farm farm) {
        return service.save(farm);
    }
}