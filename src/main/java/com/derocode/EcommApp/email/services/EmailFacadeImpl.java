package com.derocode.EcommApp.email.services;


import com.derocode.EcommApp.email.EmailFacade;
import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.events.SharedShipmentEventDto;
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
    public void sendOrderConfirmationEmail(@NonNull SharedOrderEventDto orderEventDto) {
        emailService.sendOrderConfirmationEmail(orderEventDto);
    }

    @Override
    public void sendPaymentConfirmationEmail(@NonNull SharedPaymentEventDto paymentEvent) {
        emailService.sendPaymentConfirmationEmail(paymentEvent);
    }

    @Override
    public void sendShipmentConfirmationEmail(@NonNull SharedShipmentEventDto shipmentEvent) {
       emailService.sendShipmentConfirmationEmail(shipmentEvent);

    }
}
