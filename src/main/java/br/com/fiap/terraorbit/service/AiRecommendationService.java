package br.com.fiap.terraorbit.service;

import br.com.fiap.terraorbit.client.GaiaAiClient;
import br.com.fiap.terraorbit.entity.AiRecommendation;
import br.com.fiap.terraorbit.entity.ClimateAlert;
import br.com.fiap.terraorbit.repository.AiRecommendationRepo;
import br.com.fiap.terraorbit.repository.FarmRepo;
import br.com.fiap.terraorbit.repository.SensorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AiRecommendationService {

    private final AiRecommendationRepo repo;
    private final SensorRepo sensorRepo;
    private final FarmRepo farmRepo;
    private final GaiaAiClient gaiaAiClient;
    private final ClimateAlertService alertService;

    public Page<AiRecommendation> findAll(Long farmId, Pageable pageable) {
        if (farmId == null) {
            return repo.findAll(pageable);
        }

        return repo.findByFarm_Id(farmId, pageable);
    }

    public AiRecommendation findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    public AiRecommendation save(AiRecommendation recommendation) {
        return repo.save(recommendation);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public AiRecommendation analyze(Long farmId) {

        var farm = farmRepo.findById(farmId).orElseThrow();

        var avgTemp = sensorRepo.findByFarm_IdAndSensorStatusAndSensorType(farmId, "ACTIVE", "TEMPERATURE")
                .stream()
                .mapToDouble(s -> s.getLastReading().doubleValue())
                .average()
                .orElse(0.0);

        var avgHmd = sensorRepo.findByFarm_IdAndSensorStatusAndSensorType(farmId, "ACTIVE", "HUMIDITY")
                .stream()
                .mapToDouble(s -> s.getLastReading().doubleValue())
                .average()
                .orElse(0.0);

        System.out.println("AVG TEMP: " + avgTemp);
        System.out.println("AVG HMD: " + avgHmd);

        if (avgTemp >= 30) {
            alertService.save(
                    new ClimateAlert("DROUGHT",
                            "HIGH",
                            "Potential drought detected",
                            LocalDateTime.now(),
                            farm));
        }

        return save(new AiRecommendation(gaiaAiClient.analyze(avgTemp, avgHmd), farm));
    }
}