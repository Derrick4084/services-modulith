package com.derocode.EcommApp.cart.api;

import jakarta.validation.constraints.NotBlank;

public record CartItemDto(
        @NotBlank
        Long productId,
        @NotBlank
        Double quantity
) {
}
