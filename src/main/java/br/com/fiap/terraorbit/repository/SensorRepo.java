package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.Sensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Long> {
    Page<Sensor> findByFarm_Id(Long farmId, Pageable pageable);
    List<Sensor> findByFarm_IdAndSensorStatusAndSensorType(Long farmId, String status, String type);
}
