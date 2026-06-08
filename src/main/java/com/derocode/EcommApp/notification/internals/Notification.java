package com.derocode.EcommApp.notification.internals;



import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
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

    NotificationType type;

    private LocalDateTime notificationDate;

    private OrderEventDto orderEventDto;

    private PaymentEventDto paymentEventDto;

    private ShipmentEventDto shipmentEventDto;
}
