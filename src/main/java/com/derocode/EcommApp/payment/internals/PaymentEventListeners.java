package com.derocode.EcommApp.payment.internals;


import com.derocode.EcommApp.events.OrderEventDto;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentEventListeners {

    private final PaymentService paymentService;

    @ApplicationModuleListener
    public void on(OrderEventDto event) {
        paymentService.paymentOrderEventHandler(event);
    }
}
