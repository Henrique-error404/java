package br.com.fiap.terraorbit.dto;

public record FarmDTO(
        Long id,
        String farmName,
        String location,
        Double farmSizeHectares,
        Long ownerId
) {

}
