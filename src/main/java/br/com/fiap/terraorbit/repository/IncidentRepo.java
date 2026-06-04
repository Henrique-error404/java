package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface IncidentRepo extends JpaRepository<Incident, Long> {
}
