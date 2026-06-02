package com.derocode.EcommApp.email.internals;


import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.email.EmailFacade;
import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;


import java.util.HashMap;
import java.util.Map;

import static com.derocode.EcommApp.email.internals.EmailTemplates.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@AllArgsConstructor
@Service
@Slf4j
public class EmailFacadeImpl implements EmailFacade {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final CustomerFacade customerFacade;
    private final OrderFacade orderFacade;


    @Override
    public void sendOrderConfirmationEmail(@NonNull OrderEventDto orderEventDto) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        helper.setFrom("contact@derocode.com");

        final String templateName = ORDER_CONFIRMATION.getTemplate();

        CustomerResponseDto customer = customerFacade.getCustomerByEmail(orderEventDto.customerEmail());
        OrderResponseDto order = orderFacade.getOrderById(orderEventDto.orderId());

        String customerName = customer.firstName() + " " + customer.lastName();

        Map<String, Object> properties = new HashMap<>();
        properties.put("customerName", customerName);
        properties.put("totalAmount", orderEventDto.totalAmount());
        properties.put("orderReference", orderEventDto.reference());
        properties.put("products", order.products());

        Context context = new Context();
        context.setVariables(properties);
        helper.setSubject(ORDER_CONFIRMATION.getSubject());

        String destinationEmail = orderEventDto.customerEmail();

        try {
            String htmlTemplate = springTemplateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(destinationEmail);
            emailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to %s with template %s", destinationEmail, templateName));
        } catch (MessagingException | MailException e) {
            log.warn("WARNING - Problem sending email to " + destinationEmail);
        }



    }

    @Override
    public void sendPaymentConfirmationEmail(@NonNull PaymentEventDto paymentEvent) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        messageHelper.setFrom("contact@derocode.com");

        final String templateName = PAYMENT_CONFIRMATION.getTemplate();

        CustomerResponseDto customer = customerFacade.getCustomerByEmail(paymentEvent.customerEmail());
        OrderResponseDto order = orderFacade.getOrderById(paymentEvent.orderId());

        String customerName = customer.firstName() + " " + customer.lastName();

        Map<String,Object> properties = new HashMap<>();
        properties.put("customerName", customerName);
        properties.put("amount", paymentEvent.amount());
        properties.put("orderReference", paymentEvent.orderReference());
        properties.put("paymentDate", paymentEvent.paymentDate());

        Context context = new Context();
        context.setVariables(properties);
        messageHelper.setSubject(PAYMENT_CONFIRMATION.getSubject());

        String destinationEmail = paymentEvent.customerEmail();

        try {
            String htmlTemplate = springTemplateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            emailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to %s with template %s", destinationEmail, templateName));

        }
        catch (MessagingException | MailException e) {
            log.warn("WARNING - Problem sending email to " + destinationEmail);
        }


    }

    @Override
    public void sendShipmentConfirmationEmail(@NonNull ShipmentEventDto shipmentEvent) throws MessagingException {

        MimeMessage mimeMessage = emailSender.createMimeMessage();

        MimeMessageHelper messageHelper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED_RELATED,
                UTF_8.name());
        messageHelper.setFrom("contact@derocode.com");

        final String templateName = SHIPMENT_CONFIRMATION.getTemplate();

        CustomerResponseDto customer = customerFacade.getCustomerByEmail(shipmentEvent.customerEmail());
        OrderResponseDto order = orderFacade.getOrderById(shipmentEvent.orderId());

        String customerName = customer.firstName() + " " + customer.lastName();

        Map<String, Object> properties = new HashMap<>();
        properties.put("orderReference", shipmentEvent.orderReference());
        properties.put("customerName", customerName);
        properties.put("carrier", shipmentEvent.carrier());
        properties.put("trackingNumber", shipmentEvent.trackingNumber());
        properties.put("estimatedDeliveryDate", shipmentEvent.estimatedDeliveryDate());

        Context context = new Context();
        context.setVariables(properties);
        messageHelper.setSubject(SHIPMENT_CONFIRMATION.getSubject());

        String destinationEmail = shipmentEvent.customerEmail();

        try {
            String htmlTemplate = springTemplateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            emailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to %s with template %s", destinationEmail, templateName));
        } catch (MessagingException | MailException e) {
            log.warn("WARNING - Problem sending email to " + destinationEmail);
        }

    }
}
