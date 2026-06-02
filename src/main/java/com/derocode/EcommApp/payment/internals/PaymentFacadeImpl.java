package com.derocode.EcommApp.payment.internals;

import com.derocode.EcommApp.payment.api.PaymentFacade;
import com.derocode.EcommApp.payment.api.PaymentResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentFacadeImpl implements PaymentFacade {

    private final PaymentRepository repository;
    private final ApplicationEventPublisher publisher;

    @Override
    public PaymentResponseDto getById(Long id) {
        Optional<Payment> payment = repository.findById(id);
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
            throw  new RuntimeException("Payment not found");
        }
    }
}
