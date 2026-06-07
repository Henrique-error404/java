package br.com.fiap.terraorbit.service;


import br.com.fiap.terraorbit.dto.request.IncidentNewRequest;
import br.com.fiap.terraorbit.entity.Incident;
import br.com.fiap.terraorbit.repository.FarmRepo;
import br.com.fiap.terraorbit.repository.IncidentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncidentService {

    private final IncidentRepo repository;
    private final FarmRepo farmRepo;

    public Page<Incident> findAll(Long farmId, Pageable pageable) {
        if (farmId == null) {
            return repository.findAll(pageable);
        }
        return repository.findByFarm_Id(farmId, pageable);
    }

    public Incident findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public Incident save(Incident incident) {
        return repository.save(incident);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Incident create(IncidentNewRequest request) {
        return save(newIncident(request));
    }

    public Incident update(Long id, IncidentNewRequest request) {
        var incident = newIncident(request);
        incident.setId(id);
        return save(incident);
    }

    private Incident newIncident(IncidentNewRequest request) {
        var farm = farmRepo.findById(request.farmId()).orElseThrow();

        return Incident.builder()
                .incidentStatus(request.incidentStatus())
                .incidentType(request.incidentType())
                .farm(farm)
                .incidentDescription(request.incidentDescription())
                .build();
    }
}