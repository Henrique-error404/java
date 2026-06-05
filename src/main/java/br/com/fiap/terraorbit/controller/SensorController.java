package br.com.fiap.terraorbit.controller;

import br.com.fiap.terraorbit.entity.Sensor;
import br.com.fiap.terraorbit.service.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService service;

    @GetMapping
    public List<Sensor> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Sensor findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Sensor create(@RequestBody Sensor sensor) {
        return service.save(sensor);
    }

    @PutMapping("/{id}")
    public Sensor update(@PathVariable Long id,
                         @RequestBody Sensor sensor) {

        sensor.setId(id);
        return service.save(sensor);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}