package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.repository.AiRecommendationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AiRecommendationService {

    private final AiRecommendationRepo repo;

    public Page<AiRecommendation> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public AiRecommendation findById(Long id) {
        return repo.findById(id)
                .orElseThrow();
    }

    public AiRecommendation save(AiRecommendation recommendation) {
        return repo.save(recommendation);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}