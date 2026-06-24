package com.derocode.EcommApp.order;

import java.util.List;

public record CreateOrderDto(
        String paymentMethod,
        String customerEmail,
        List<CreateOrderItemDto> products
) {
}
