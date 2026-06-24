package com.derocode.EcommApp.product.listeners;

import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.product.services.ProductEventHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentEventListener {

    private final ProductEventHandlerService handlerService;

    @ApplicationModuleListener
    public void on(SharedPaymentEventDto paymentEventDto) {
        handlerService.handlePaymentEvent(paymentEventDto);
    }
}
