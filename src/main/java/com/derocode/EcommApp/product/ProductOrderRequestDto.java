package com.derocode.EcommApp.product;

public record ProductOrderRequestDto(
        Long productId,
        Double quantity
) {
}
