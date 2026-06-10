package com.derocode.EcommApp.payment.repositories;

import com.derocode.EcommApp.payment.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Boolean existsByOrderId(Long orderId);
}
