package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.entity.ClimateAlert;
import br.com.fiap.terraorbit.repository.ClimateAlertRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClimateAlertService {

    private final ClimateAlertRepo repository;

    public List<ClimateAlert> findAll() {
        return repository.findAll();
    }

    public ClimateAlert findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public ClimateAlert save(ClimateAlert alert) {
        return repository.save(alert);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}