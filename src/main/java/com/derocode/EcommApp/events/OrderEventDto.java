package com.derocode.EcommApp.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderEventDto(
        Long orderId,
        String reference,
        String status,
        BigDecimal totalAmount,
        String paymentMethod,
        LocalDateTime orderDate,
        String customerEmail
) {
}
