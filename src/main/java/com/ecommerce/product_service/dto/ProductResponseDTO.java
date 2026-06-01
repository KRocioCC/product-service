package com.ecommerce.product_service.dto;

public record ProductResponseDTO(
        String id,
        String name,
        String description,
        String price
) {
}
