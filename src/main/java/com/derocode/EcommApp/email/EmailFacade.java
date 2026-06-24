package com.derocode.EcommApp.email;


import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.events.SharedShipmentEventDto;
import jakarta.mail.MessagingException;

public interface EmailFacade {

    void sendOrderConfirmationEmail(SharedOrderEventDto orderEventDto) throws MessagingException;

    void sendPaymentConfirmationEmail(SharedPaymentEventDto paymentEventDto) throws MessagingException;

    void sendShipmentConfirmationEmail(SharedShipmentEventDto shipmentEventDto) throws MessagingException;

}
