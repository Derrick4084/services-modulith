package com.derocode.EcommApp.notification.services;


import com.derocode.EcommApp.email.EmailFacade;
import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import com.derocode.EcommApp.notification.repository.NotificationDatabaseSequence;
import com.derocode.EcommApp.notification.repository.NotificationRepository;
import com.derocode.EcommApp.notification.enums.NotificationType;
import com.derocode.EcommApp.notification.models.Notification;
import jakarta.mail.MessagingException;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class NotificationService {

    private final MongoOperations mongoOperations;
    private final NotificationRepository notificationRepository;
    private final EmailFacade emailFacade;

    public NotificationService(@Qualifier("notificationMongoTemplate") MongoOperations mongoOperations, NotificationRepository notificationRepository, EmailFacade emailFacade) {
        this.mongoOperations = mongoOperations;
        this.notificationRepository = notificationRepository;
        this.emailFacade = emailFacade;
    }

    public long generateSequence(String seqName) {
        NotificationDatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                NotificationDatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }


    @Transactional
    public void handleOrderEvent(@NonNull OrderEventDto orderEventDto) {

        if(Objects.equals(orderEventDto.status(),"CREATED")) {

            Notification notification = Notification.builder()
                    .id(generateSequence(Notification.GROUP_SEQUENCE))
                    .notificationDate(LocalDateTime.now())
                    .type(NotificationType.ORDER_EVENT)
                    .orderEventDto(orderEventDto)
                    .build();
            notificationRepository.save(notification);

            // Need to send order conf email
            try {
                emailFacade.sendOrderConfirmationEmail(orderEventDto);
            } catch (MessagingException e) {
                  throw new RuntimeException(e.getMessage());
            }
        }
    }



    @Transactional
    public void handlePaymentEvent(@NonNull PaymentEventDto paymentEventDto) {


        if(Objects.equals(paymentEventDto.status(),"ACCEPTED")) {

            Notification notification = Notification.builder()
                    .id(generateSequence(Notification.GROUP_SEQUENCE))
                    .notificationDate(LocalDateTime.now())
                    .type(NotificationType.PAYMENT_EVENT)
                    .paymentEventDto(paymentEventDto)
                    .build();

            notificationRepository.save(notification);

            // need to send payment conf email

            try {
                emailFacade.sendPaymentConfirmationEmail(paymentEventDto);
            } catch (MessagingException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
    }


    @Transactional
    public void handleShipmentEvent(@NonNull ShipmentEventDto shipmentEventDto) {

        if(Objects.equals(shipmentEventDto.status(),"SHIPPED")) {

            Notification notification = Notification.builder()
                    .id(generateSequence(Notification.GROUP_SEQUENCE))
                    .notificationDate(LocalDateTime.now())
                    .type(NotificationType.SHIPMENT_EVENT)
                    .shipmentEventDto(shipmentEventDto)
                    .build();

            notificationRepository.save(notification);


            // Send shipping conf email
            try {
                emailFacade.sendShipmentConfirmationEmail(shipmentEventDto);
            } catch (MessagingException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
    }
}
