package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.dto.request.FarmNewRequest;
import br.com.fiap.terraorbit.entity.Farm;
import br.com.fiap.terraorbit.repository.FarmRepo;
import br.com.fiap.terraorbit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepo repository;
    private final UserRepo userRepo;

    public Page<Farm> findAll(Long userId, Pageable pageable) {
        if (userId == null) {
            return repository.findAll(pageable);
        }
        return repository.findByOwner_Id(userId, pageable);
    }

    public Farm findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public Farm save(Farm farm) {
        return repository.save(farm);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Farm create(FarmNewRequest request) {
        return save(newFarm(request));
    }

    private Farm newFarm(FarmNewRequest request) {
        var user = userRepo.findById(request.ownerId()).orElseThrow();

        return Farm.builder()
                .farmName(request.name())
                .farmSizeHectares(request.farmSizeHectares())
                .location(request.location())
                .owner(user)
                .build();
    }

    public Farm update(Long id, FarmNewRequest request) {
        var farm = newFarm(request);
        farm.setId(id);
        return save(farm);
    }
}