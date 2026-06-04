package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Long> {
}
