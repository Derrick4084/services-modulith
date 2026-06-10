package com.derocode.EcommApp.product.models;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "inventory_reservation", schema = "services",
indexes = {
        @Index(name = "orderId_idx", columnList = "orderId")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private Long productId;
    private Double quantity;
}
