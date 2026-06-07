package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.Incident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IncidentRepo extends JpaRepository<Incident, Long> {
    Page<Incident> findByFarm_Id(Long farmId, Pageable pageable);
}
