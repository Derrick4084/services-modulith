package com.derocode.EcommApp.order.api;

public record CreateOrderItemDto(
        Long productId,
        Double quantity
) {
}
