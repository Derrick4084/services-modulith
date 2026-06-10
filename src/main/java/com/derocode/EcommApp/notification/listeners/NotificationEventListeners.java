package com.derocode.EcommApp.notification.listeners;

import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import com.derocode.EcommApp.notification.services.NotificationService;
import org.jspecify.annotations.NonNull;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListeners {

    private final NotificationService notificationService;

    public NotificationEventListeners(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @ApplicationModuleListener
    public void on(@NonNull OrderEventDto orderEventDto) {
        notificationService.handleOrderEvent(orderEventDto);

    }

    @ApplicationModuleListener
    public void on(@NonNull PaymentEventDto paymentEventDto) {
        notificationService.handlePaymentEvent(paymentEventDto);

    }

    @ApplicationModuleListener
    public void on(@NonNull ShipmentEventDto shipmentEventDto) {
        notificationService.handleShipmentEvent(shipmentEventDto);

    }
}
