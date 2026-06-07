package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.AiRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRecommendationRepo extends JpaRepository<AiRecommendation, Long> {
    Page<AiRecommendation> findByFarm_Id(Long farmId, Pageable pageable);
}
