package br.com.fiap.terraorbit.dto.request;

import java.math.BigDecimal;

public record FarmNewRequest(
        String name,
        String location,
        BigDecimal farmSizeHectares,
        Long ownerId
) {
}
