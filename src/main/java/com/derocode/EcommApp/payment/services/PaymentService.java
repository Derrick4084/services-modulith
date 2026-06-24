package com.derocode.EcommApp.payment.services;


import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.enums.SharedPaymentMethod;
import com.derocode.EcommApp.enums.SharedPaymentStatus;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.payment.api.PaymentResponseDto;
import com.derocode.EcommApp.payment.models.Payment;
import com.derocode.EcommApp.payment.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;

    public PaymentResponseDto getById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            Payment response = payment.get();
            return new PaymentResponseDto(
                    response.getId(),
                    response.getAmount(),
                    response.getPaymentMethod().name(),
                    response.getStatus().name(),
                    response.getOrderId(),
                    response.getOrderReference(),
                    response.getPaymentDate()
            );
        }
        else {
            throw  new SharedResourceNotFoundException("Payment not found");
        }
    }

    @Transactional
    public void paymentOrderEventHandler(@NonNull SharedOrderEventDto event) {

        if (Objects.equals(event.status(), "CREATED")) {

            // Helps with idempotency for event retries
            if(paymentRepository.existsByOrderId(event.orderId())) {
                return;
            }

            Payment payment = Payment.builder()
                    .orderId(event.orderId())
                    .orderReference(event.reference())
                    .status(SharedPaymentStatus.ACCEPTED)
                    .paymentDate(LocalDateTime.now())
                    .paymentMethod(SharedPaymentMethod.valueOf(event.paymentMethod()))
                    .amount(event.totalAmount())
                    .build();

            Payment savedPayment = paymentRepository.save(payment);

            SharedPaymentEventDto paymentEventDto = new SharedPaymentEventDto(
                    savedPayment.getId(),
                    savedPayment.getPaymentDate(),
                    savedPayment.getPaymentMethod().name(),
                    savedPayment.getOrderId(),
                    savedPayment.getOrderReference(),
                    savedPayment.getAmount(),
                    savedPayment.getStatus().name(),
                    event.customerEmail()
            );
            publisher.publishEvent(paymentEventDto);
            System.out.println("Published payment event");

        }
    }





}
