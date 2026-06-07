package br.com.fiap.terraorbit.dto.response;

import br.com.fiap.terraorbit.entity.Sensor;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Relation(collectionRelation = "sensors", itemRelation = "sensor")
public record SensorDTO(
        Long id,
        String sensorName,
        String sensorType,
        String sensorStatus,
        BigDecimal lastReading,
        LocalDateTime installedAt,
        Long farmId
) {


    public static SensorDTO fromEntity(Sensor entity) {
        return new SensorDTO(
                entity.getId(),
                entity.getSensorName(),
                entity.getSensorType(),
                entity.getSensorStatus(),
                entity.getLastReading(),
                entity.getInstalledAt(),
                entity.getFarm().getId()
        );
    }
}
