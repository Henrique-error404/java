package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.entity.Farm;
import br.com.fiap.terraorbit.repository.FarmRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepo repository;

    public List<Farm> findAll() {
        return repository.findAll();
    }

    public Farm findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public Farm save(Farm farm) {
        return repository.save(farm);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}