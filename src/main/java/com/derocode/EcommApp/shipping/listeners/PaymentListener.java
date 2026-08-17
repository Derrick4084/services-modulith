package com.derocode.EcommApp.shipping.listeners;


import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.shipping.services.ShipmentEventHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentListener {

    private final ShipmentEventHandlerService eventHandlerService;

    @ApplicationModuleListener
    public void on(SharedPaymentEventDto event) {
        eventHandlerService.handlePaymentEvent(event);
    }
}
