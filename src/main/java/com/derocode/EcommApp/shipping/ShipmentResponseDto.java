package com.derocode.EcommApp.shipping;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ShipmentResponseDto(
        Long id,
        // Relationship
        Long orderId,
        // Address snapshot
        String recipientName,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        String phoneNumber,
        // Shipping details
        String shipmentMethod,
        String carrier,
        String trackingNumber,
        String status,
        Double shippingCost,
        // Timestamps
        LocalDateTime shippedAt,
        LocalDateTime deliveredAt,
        LocalDate estimatedDeliveryDate,
        // Audit
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
