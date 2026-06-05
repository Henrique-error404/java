package br.com.fiap.terraorbit.service;


import br.com.fiap.terraorbit.entity.Incident;
import br.com.fiap.terraorbit.repository.IncidentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepo repository;

    public List<Incident> findAll() {
        return repository.findAll();
    }

    public Incident findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public Incident save(Incident incident) {
        return repository.save(incident);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}