package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.entity.ClimateAlert;
import br.com.fiap.terraorbit.repository.ClimateAlertRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClimateAlertService {

    private final ClimateAlertRepo repository;

    public Page<ClimateAlert> findAll(Long farmId, Pageable pageable) {
        if (farmId == null) {
            return repository.findAll(pageable);
        }
        return repository.findByFarm_Id(farmId, pageable);
    }

    public ClimateAlert findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public void save(ClimateAlert alert) {
        repository.save(alert);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}