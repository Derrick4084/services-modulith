package com.derocode.EcommApp.payment.models;

import com.derocode.EcommApp.enums.SharedPaymentMethod;
import com.derocode.EcommApp.enums.SharedPaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "payment", schema = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    @Enumerated(STRING)
    private SharedPaymentMethod paymentMethod;

    @Enumerated(STRING)
    private SharedPaymentStatus status;

    private Long orderId;

    private String orderReference;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime paymentDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;
}

