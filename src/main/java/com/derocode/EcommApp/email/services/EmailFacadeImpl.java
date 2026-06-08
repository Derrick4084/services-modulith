package com.derocode.EcommApp.email.services;


import com.derocode.EcommApp.email.EmailFacade;
import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
@Slf4j
public class EmailFacadeImpl implements EmailFacade {

    private final EmailService emailService;

    @Override
    public void sendOrderConfirmationEmail(@NonNull OrderEventDto orderEventDto) {
        emailService.sendOrderConfirmationEmail(orderEventDto);
    }

    @Override
    public void sendPaymentConfirmationEmail(@NonNull PaymentEventDto paymentEvent) {
        emailService.sendPaymentConfirmationEmail(paymentEvent);
    }

    @Override
    public void sendShipmentConfirmationEmail(@NonNull ShipmentEventDto shipmentEvent) {
       emailService.sendShipmentConfirmationEmail(shipmentEvent);

    }
}
