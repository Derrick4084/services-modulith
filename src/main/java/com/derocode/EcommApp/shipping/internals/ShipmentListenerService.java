package com.derocode.EcommApp.shipping.internals;


import com.derocode.EcommApp.events.PaymentEventDto;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@AllArgsConstructor
public class ShipmentListenerService {

    private final ShipmentService shipmentService;

    @ApplicationModuleListener
    public void on(PaymentEventDto event) {
        shipmentService.handlePaymentEvent(event);
    }
}
