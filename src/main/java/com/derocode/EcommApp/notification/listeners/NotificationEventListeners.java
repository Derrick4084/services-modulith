package com.derocode.EcommApp.notification.listeners;

import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.events.SharedShipmentEventDto;
import com.derocode.EcommApp.notification.services.NotificationEventHandlerService;
import org.jspecify.annotations.NonNull;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListeners {

    private final NotificationEventHandlerService eventHandlerService;

    public NotificationEventListeners(NotificationEventHandlerService eventHandlerService) {
        this.eventHandlerService = eventHandlerService;
    }

    @ApplicationModuleListener
    public void on(@NonNull SharedOrderEventDto orderEventDto) {
        eventHandlerService.handleOrderEvent(orderEventDto);

    }

    @ApplicationModuleListener
    public void on(@NonNull SharedPaymentEventDto paymentEventDto) {
        eventHandlerService.handlePaymentEvent(paymentEventDto);

    }

    @ApplicationModuleListener
    public void on(@NonNull SharedShipmentEventDto shipmentEventDto) {
        eventHandlerService.handleShipmentEvent(shipmentEventDto);

    }
}
