package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.repository.AiRecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiRecommendationService {

    private final AiRecommendationRepo repository;

    public List<AiRecommendation> findAll() {
        return repository.findAll();
    }

    public AiRecommendation findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public AiRecommendation save(AiRecommendation recommendation) {
        return repository.save(recommendation);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}