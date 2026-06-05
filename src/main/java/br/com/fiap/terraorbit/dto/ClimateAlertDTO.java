package br.com.fiap.terraorbit.dto;

import java.time.LocalDateTime;

public record ClimateAlertDTO(
        Long id,
        String alertType,
        String severity,
        String message,
        LocalDateTime alertDate,
        Long farmId
) {
}
