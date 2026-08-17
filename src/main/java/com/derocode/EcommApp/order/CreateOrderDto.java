package com.derocode.EcommApp.order;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateOrderDto(
        @NotBlank
        String paymentMethod,
        @NotBlank
        String customerEmail,
        @NotBlank
        List<CreateOrderItemDto> products
) {
}
