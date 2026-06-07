package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.ClimateAlert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimateAlertRepo extends JpaRepository<ClimateAlert, Long> {
    Page<ClimateAlert> findByFarm_Id(Long farmId, Pageable pageable);
}
