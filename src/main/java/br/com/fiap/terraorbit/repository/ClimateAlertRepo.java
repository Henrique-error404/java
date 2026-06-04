package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.ClimateAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClimateAlertRepo extends JpaRepository<ClimateAlert, Long> {
}
