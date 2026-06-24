package com.derocode.EcommApp.events;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SharedPaymentEventDto(
        Long paymentId,
        LocalDateTime paymentDate,
        String paymentMethod,
        Long orderId,
        String orderReference,
        BigDecimal amount,
        String status,
        String customerEmail
) {
}
