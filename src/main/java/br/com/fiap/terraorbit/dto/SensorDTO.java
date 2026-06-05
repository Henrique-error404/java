package br.com.fiap.terraorbit.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
