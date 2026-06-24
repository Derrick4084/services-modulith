package com.derocode.EcommApp.cart.api;

public record CartItemDto(
        Long productId,
        Double quantity
) {
}
