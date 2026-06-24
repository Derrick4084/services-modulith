package com.derocode.EcommApp.order;

public record CreateOrderItemDto(
        Long productId,
        Double quantity
) {
}
