package com.derocode.EcommApp.email.services;


import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.CustomerResponseDto;
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

import static com.derocode.EcommApp.email.services.EmailTemplates.*;
import static com.derocode.EcommApp.email.services.EmailTemplates.SHIPMENT_CONFIRMATION;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine springTemplateEngine;
    private final CustomerFacade customerFacade;
    private final OrderFacade orderFacade;

    public void sendOrderConfirmationEmail(@NonNull OrderEventDto orderEventDto) {

        try {
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


            String htmlTemplate = springTemplateEngine.process(templateName, context);
            helper.setText(htmlTemplate, true);
            helper.setTo(destinationEmail);
            emailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to %s with template %s", destinationEmail, templateName));
        } catch (MessagingException | MailException e) {
            log.warn("WARNING - Problem sending email to " + orderEventDto.customerEmail());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendPaymentConfirmationEmail(@NonNull PaymentEventDto paymentEvent) {

        try {

            MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(
                    mimeMessage,
                    MULTIPART_MODE_MIXED_RELATED,
                    UTF_8.name());
            messageHelper.setFrom("contact@derocode.com");

            final String templateName = PAYMENT_CONFIRMATION.getTemplate();

            CustomerResponseDto customer = customerFacade.getCustomerByEmail(paymentEvent.customerEmail());

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


            String htmlTemplate = springTemplateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            emailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to %s with template %s", destinationEmail, templateName));

        }
        catch (MessagingException | MailException e) {
            log.warn("WARNING - Problem sending email to " + paymentEvent.customerEmail());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendShipmentConfirmationEmail(@NonNull ShipmentEventDto shipmentEvent) {

        try {
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


            String htmlTemplate = springTemplateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destinationEmail);
            emailSender.send(mimeMessage);
            log.info(String.format("INFO - Email sent to %s with template %s", destinationEmail, templateName));
        } catch (MessagingException | MailException e) {
            log.warn("WARNING - Problem sending email to " + shipmentEvent.customerEmail());
        }

    }
}
