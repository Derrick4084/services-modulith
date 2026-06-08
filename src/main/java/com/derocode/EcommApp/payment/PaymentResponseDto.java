package com.derocode.EcommApp.payment;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponseDto(
        Long id,
        BigDecimal amount,
        String paymentMethod,
        String status,
        Long orderId,
        String orderReference,
        LocalDateTime paymentDate
) {
}
