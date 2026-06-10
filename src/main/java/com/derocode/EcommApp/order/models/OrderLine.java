package com.derocode.EcommApp.order.models;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_line", schema = "services")
public class OrderLine {
    @Id
    @Column(name = "ID", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    private Long productId;

    private String productName;

    private BigDecimal price;

    private double quantity;

    private BigDecimal lineTotal;
}
