package com.derocode.EcommApp.order;

import com.derocode.EcommApp.order.api.CreateOrderItemDto;

import java.util.List;

public record CreateOrderDto(
        String paymentMethod,
        String customerEmail,
        List<CreateOrderItemDto> products
) {
}
