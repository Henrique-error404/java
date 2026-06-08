package br.com.fiap.terraorbit.service;


import br.com.fiap.terraorbit.dto.request.SensorNewRequest;
import br.com.fiap.terraorbit.entity.Sensor;
import br.com.fiap.terraorbit.repository.FarmRepo;
import br.com.fiap.terraorbit.repository.SensorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepo repository;
    private final FarmRepo farmRepo;

    public Page<Sensor> findAll(Long farmId, Pageable pageable) {
        if (farmId == null) {
            return repository.findAll(pageable);
        }
        return repository.findByFarm_Id(farmId, pageable);
    }

    public Sensor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    public Sensor save(Sensor Sensor) {
        return repository.save(Sensor);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Sensor create(SensorNewRequest request) {
        return save(newSensor(request));
    }

    public Sensor update(Long id, SensorNewRequest request) {
        var Sensor = newSensor(request);
        Sensor.setId(id);
        return save(Sensor);
    }

    private Sensor newSensor(SensorNewRequest request) {
        var farm = farmRepo.findById(request.farmId()).orElseThrow();

        return Sensor.builder()
                .sensorName(request.sensorName())
                .sensorStatus(request.sensorStatus())
                .sensorType(request.sensorType())
                .farm(farm)
                .lastReading(request.lastReading())
                .build();
    }
}