package com.derocode.EcommApp.cart.api;

import jakarta.validation.constraints.NotBlank;

public record ProcessCartDto(
        @NotBlank
        String paymentMethod
) {
}
