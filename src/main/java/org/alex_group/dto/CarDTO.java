package org.alex_group.dto;

public record CarDTO(
        String markName,
        String modelName,
        Integer productionYear,
        Integer price,
        String productionCountry,
        String colour,
        Integer count
) {
}
