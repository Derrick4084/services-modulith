package com.derocode.EcommApp.shipping.internals;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shipments", schema = "services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationship
    private Long orderId;

    // Address snapshot
    private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phoneNumber;

    // Shipping details
    @Enumerated(EnumType.STRING)
    private ShipmentMethod shipmentMethod;

    @Enumerated(EnumType.STRING)
    private ShipmentCarrier carrier;

    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private Double shippingCost;

    // Timestamps
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDate estimatedDeliveryDate;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
