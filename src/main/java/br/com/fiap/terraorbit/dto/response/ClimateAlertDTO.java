package br.com.fiap.terraorbit.dto.response;

import br.com.fiap.terraorbit.entity.ClimateAlert;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(collectionRelation = "alerts", itemRelation = "alert")
public record ClimateAlertDTO(
        Long id,
        String alertType,
        String severity,
        String message,
        LocalDateTime alertDate,
        Long farmId
) {
    public static ClimateAlertDTO fromEntity(ClimateAlert entity) {
        return new ClimateAlertDTO(
                entity.getId(),
                entity.getAlertType(),
                entity.getSeverity(),
                entity.getMessage(),
                entity.getAlertDate(),
                entity.getFarm().getId()
        );
    }
}
