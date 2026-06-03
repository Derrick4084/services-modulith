package com.derocode.EcommApp.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        String reference,
        String status,
        BigDecimal totalAmount,
        String paymentMethod,
        LocalDateTime orderDate,
        String customerEmail,
        List<OrderLineDto> products
) {
}
