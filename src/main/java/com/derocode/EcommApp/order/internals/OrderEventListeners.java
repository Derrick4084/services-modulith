package com.derocode.EcommApp.order.internals;

import com.derocode.EcommApp.events.ShipmentEventDto;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OrderEventListeners {

    private final OrderService orderService;

    @ApplicationModuleListener
    public void on(ShipmentEventDto shipmentEventDto) {
        orderService.handleShipmentEvent(shipmentEventDto);

    }
}
