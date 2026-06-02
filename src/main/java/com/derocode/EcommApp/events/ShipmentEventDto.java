package com.derocode.EcommApp.events;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ShipmentEventDto(
        Long shipmentId,
        Long orderId,
        String orderReference,
        LocalDateTime createdAt,
        String status, // PENDING, SHIPPED, DELIVERED
        String trackingNumber,
        String carrier,
        String customerEmail,
        LocalDate estimatedDeliveryDate
) {
}
