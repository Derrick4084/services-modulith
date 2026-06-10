package com.derocode.EcommApp.product.listeners;

import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.product.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentEventListener {

    private final ProductService productService;

    @ApplicationModuleListener
    public void on(PaymentEventDto paymentEventDto) {
        productService.handlePaymentEvent(paymentEventDto);
    }
}
