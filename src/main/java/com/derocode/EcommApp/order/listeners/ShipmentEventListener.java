package com.derocode.EcommApp.order.listeners;

import com.derocode.EcommApp.events.SharedShipmentEventDto;
import com.derocode.EcommApp.order.services.OrderEventHandlerService;
import lombok.AllArgsConstructor;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShipmentEventListener {

    private final OrderEventHandlerService handlerService;

    @ApplicationModuleListener
    public void on(SharedShipmentEventDto shipmentEventDto) {
        handlerService.handleShipmentEvent(shipmentEventDto);

    }
}
