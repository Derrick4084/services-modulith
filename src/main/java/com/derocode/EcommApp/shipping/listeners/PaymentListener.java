package com.derocode.EcommApp.shipping.listeners;


import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.shipping.services.ShipmentService;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PaymentListener {

    private final ShipmentService shipmentService;

    @ApplicationModuleListener
    public void on(PaymentEventDto event) {
        shipmentService.handlePaymentEvent(event);
    }
}
