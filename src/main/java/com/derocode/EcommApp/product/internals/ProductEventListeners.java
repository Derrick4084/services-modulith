package com.derocode.EcommApp.product.internals;

import com.derocode.EcommApp.events.PaymentEventDto;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductEventListeners {

    private final ProductService productService;

    @ApplicationModuleListener
    public void on(PaymentEventDto paymentEventDto) {
        productService.handlePaymentEvent(paymentEventDto);
    }
}
