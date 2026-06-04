package br.com.fiap.terraorbit.repository;

import br.com.fiap.terraorbit.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmRepo extends JpaRepository<Farm, Long> {
}
