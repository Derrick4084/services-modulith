package com.derocode.EcommApp.email;


import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import jakarta.mail.MessagingException;

public interface EmailFacade {

    void sendOrderConfirmationEmail(OrderEventDto orderEventDto) throws MessagingException;

    void sendPaymentConfirmationEmail(PaymentEventDto paymentEventDto) throws MessagingException;

    void sendShipmentConfirmationEmail(ShipmentEventDto shipmentEventDto) throws MessagingException;

}
