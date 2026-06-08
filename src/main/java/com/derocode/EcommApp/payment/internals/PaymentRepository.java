package com.derocode.EcommApp.payment.internals;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Boolean existsByOrderId(Long orderId);
}
