package com.derocode.EcommApp;

import com.derocode.EcommApp.enums.PaymentMethod;
import com.derocode.EcommApp.enums.PaymentStatus;
import com.derocode.EcommApp.payment.models.Payment;
import com.derocode.EcommApp.payment.repositories.PaymentRepository;
import com.derocode.EcommApp.payment.services.PaymentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class PaymentRepositoryTest {

    @Autowired
    PaymentRepository repository;

    @Autowired
    PaymentService paymentService;

    @Test
    void shouldCreatePayment() {

        Payment payment = Payment.builder()
                .orderId(1000L)
                .orderReference("ODR-123")
                .status(PaymentStatus.ACCEPTED)
                .paymentDate(LocalDateTime.now())
                .paymentMethod(PaymentMethod.DISCOVER_CARD)
                .amount(BigDecimal.valueOf(500.00))
                .build();

        Payment savedPayment = repository.save(payment);


        assertThat(savedPayment.getOrderReference())
                .isEqualTo("ODR-123");

        assertThat(repository.existsByOrderId(savedPayment.getOrderId()));




    }


}
