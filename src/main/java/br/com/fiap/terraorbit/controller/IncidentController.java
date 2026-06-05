package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.entity.Incident;
import br.com.fiap.terraorbit.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService service;

    @GetMapping
    public List<Incident> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Incident findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Incident create(@RequestBody Incident incident) {
        return service.save(incident);
    }

    @PutMapping("/{id}")
    public Incident update(@PathVariable Long id,
                           @RequestBody Incident incident) {

        incident.setId(id);
        return service.save(incident);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}