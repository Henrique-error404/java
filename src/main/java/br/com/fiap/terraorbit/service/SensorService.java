package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.entity.Sensor;
import br.com.fiap.terraorbit.repository.SensorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepo repository;

    public List<Sensor> findAll() {
        return repository.findAll();
    }

    public Sensor findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public Sensor save(Sensor sensor) {
        return repository.save(sensor);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}