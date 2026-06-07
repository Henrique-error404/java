package br.com.fiap.terraorbit.dto.request;

import java.math.BigDecimal;

public record SensorNewRequest(
        String sensorName,
        String sensorType,
        String sensorStatus,
        BigDecimal lastReading,
        Long farmId
) {
}
