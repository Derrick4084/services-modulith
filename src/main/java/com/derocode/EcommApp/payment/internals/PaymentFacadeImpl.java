package com.derocode.EcommApp.payment.internals;

import com.derocode.EcommApp.payment.PaymentFacade;
import com.derocode.EcommApp.payment.PaymentResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentFacadeImpl implements PaymentFacade {

    private final PaymentService paymentService;

    @Override
    public PaymentResponseDto getById(Long id) {

        return paymentService.getById(id);
    }

}
