package com.derocode.EcommApp.payment.internals;


import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.enums.PaymentMethod;
import com.derocode.EcommApp.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PaymentServiceImpl {

    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void paymentOrderEventHandler(@NonNull OrderEventDto event) {

        if (Objects.equals(event.status(), "CREATED")) {

            Payment payment = Payment.builder()
                    .orderId(event.orderId())
                    .orderReference(event.reference())
                    .status(PaymentStatus.ACCEPTED)
                    .paymentDate(LocalDateTime.now())
                    .paymentMethod(PaymentMethod.valueOf(event.paymentMethod()))
                    .amount(event.totalAmount())
                    .build();

            Payment savedPayment = paymentRepository.save(payment);

            PaymentEventDto paymentEventDto = new PaymentEventDto(
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
