package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.AiRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRecommendationRepo extends JpaRepository<AiRecommendation, Long> {
}
