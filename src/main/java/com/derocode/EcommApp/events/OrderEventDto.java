package com.derocode.EcommApp.events;

import com.derocode.EcommApp.order.OrderLineDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderEventDto(
        Long orderId,
        String reference,
        String status,
        BigDecimal totalAmount,
        String paymentMethod,
        LocalDateTime orderDate,
        String customerEmail
//        List<OrderLineDto> products
) {
}
