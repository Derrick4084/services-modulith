package com.derocode.EcommApp.notification.models;



import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.events.SharedPaymentEventDto;
import com.derocode.EcommApp.events.SharedShipmentEventDto;

import com.derocode.EcommApp.notification.enums.NotificationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Transient
    public static final String GROUP_SEQUENCE = "all_notifications";

    @Transient
    public static final String ORDER_SEQUENCE = "order_notifications";

    @Transient
    public static final String PAYMENT_SEQUENCE = "payment_notifications";

    @Transient
    public static final String SHIPPING_SEQUENCE = "shipping_notifications";

    @Id
    private Long id;

    @Indexed
    private Long orderId;

    NotificationType type;

    private LocalDateTime notificationDate;

    private SharedOrderEventDto orderEventDto;

    private SharedPaymentEventDto paymentEventDto;

    private SharedShipmentEventDto shipmentEventDto;
}
