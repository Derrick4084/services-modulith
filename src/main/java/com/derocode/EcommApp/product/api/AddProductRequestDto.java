package com.derocode.EcommApp.product.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AddProductRequestDto(
        @NotBlank
        String productName,
        String description,
        @Size(min = 1)
        Double quantity,
        @NotBlank
        BigDecimal price,
        String category
) {
}
