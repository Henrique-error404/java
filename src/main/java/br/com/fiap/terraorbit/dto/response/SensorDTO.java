package br.com.fiap.terraorbit.dto.response;

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
}
