package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.client.GaiaAiClient;
import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.repository.AiRecommendationRepo;
import br.com.fiap.terraorbit.repository.FarmRepo;
import br.com.fiap.terraorbit.repository.SensorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AiRecommendationService {

    private final AiRecommendationRepo repo;
    private final SensorRepo sensorRepo;
    private final FarmRepo farmRepo;
    private final GaiaAiClient gaiaAiClient;

    public Page<AiRecommendation> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public AiRecommendation findById(Long id) {
        return repo.findById(id)
                .orElseThrow();
    }

    public AiRecommendation save(AiRecommendation recommendation) {
        return repo.save(recommendation);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public AiRecommendation analyze(Long farmId) {

        var farm = farmRepo.findById(farmId).orElseThrow();

        var avgTemp = sensorRepo.findAll().stream()
                .filter(s -> s.getFarm().getId().equals(farmId) &&
                        s.getSensorStatus().equals("ACTIVE") &&
                        s.getSensorType().equals("TEMPERATURE"))
                .mapToDouble(s -> s.getLastReading().doubleValue())
                .average()
                .orElse(0.0);

        var avgHmd = sensorRepo.findAll().stream()
                .filter(s -> s.getFarm().getId().equals(farmId) &&
                        s.getSensorStatus().equals("ACTIVE") &&
                        s.getSensorType().equals("HUMIDITY"))
                .mapToDouble(s -> s.getLastReading().doubleValue())
                .average()
                .orElse(0.0);

        System.out.println("Temperature: " + avgTemp);
        System.out.println("Humidity: " + avgHmd);

        return save(new AiRecommendation(gaiaAiClient.analyze(avgTemp, avgHmd), farm));
    }
}