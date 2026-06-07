package br.com.fiap.terraorbit.dto.response;

import java.time.LocalDateTime;

public record FarmDTO(
        Long id,
        String farmName,
        String location,
        Double farmSizeHectares,
        Long ownerId,
        LocalDateTime createAt
) {

}
