package com.derocode.EcommApp.order.listeners;

import com.derocode.EcommApp.events.ShipmentEventDto;
import com.derocode.EcommApp.order.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShipmentEventListener {

    private final OrderService orderService;

    @ApplicationModuleListener
    public void on(ShipmentEventDto shipmentEventDto) {
        orderService.handleShipmentEvent(shipmentEventDto);

    }
}
