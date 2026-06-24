package com.derocode.EcommApp.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SharedOrderEventDto(
        Long orderId,
        String reference,
        String status,
        BigDecimal totalAmount,
        String paymentMethod,
        LocalDateTime orderDate,
        String customerEmail
) {
}
