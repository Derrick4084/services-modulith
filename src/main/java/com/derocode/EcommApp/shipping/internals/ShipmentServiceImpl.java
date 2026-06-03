package com.derocode.EcommApp.shipping.internals;


import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.events.PaymentEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl {


    private final CustomerFacade customerFacade;
    private final ShipmentRepository shipmentRepository;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void handlePaymentEvent(@NonNull PaymentEventDto event) {


        if(Objects.equals(event.status(), "ACCEPTED")) {

            CustomerResponseDto customer = customerFacade.getCustomerByEmail(event.customerEmail());
            String addressLine1 = customer.addresses().getFirst().houseNumber() + " " + customer.addresses().getFirst().street();
            String fullName = customer.firstName() + " " + customer.lastName();

            ShipmentMethod shipmentMethod = ShipmentMethod.EXPRESS;

            Shipment shipment = Shipment.builder()
                    .orderId(event.orderId())
                    .recipientName(fullName)
                    .createdAt(LocalDateTime.now())
                    .addressLine1(addressLine1)
                    .city(customer.addresses().getFirst().city())
                    .state(customer.addresses().getFirst().state())
                    .postalCode(customer.addresses().getFirst().zipCode())
                    .shipmentMethod(shipmentMethod)
                    .carrier(ShipmentCarrier.FEDEX)
                    .status(ShipmentStatus.SHIPPED)
                    .shippedAt(LocalDateTime.now())
                    .trackingNumber(java.util.UUID.randomUUID().toString().split("-")[0])
                    .build();
            shipment.setEstimatedDeliveryDate(LocalDate.now().plusDays(shipment.getShipmentMethod().getAddedDays()));

            Shipment savedShipment = shipmentRepository.save(shipment);

            ShipmentEventDto shipmentEvent = new ShipmentEventDto(
                    savedShipment.getId(),
                    savedShipment.getOrderId(),
                    event.orderReference(),
                    savedShipment.getCreatedAt(),
                    savedShipment.getStatus().name(),
                    savedShipment.getTrackingNumber(),
                    savedShipment.getCarrier().name(),
                    customer.email(),
                    savedShipment.getEstimatedDeliveryDate()
            );

            publisher.publishEvent(shipmentEvent);
            System.out.println("Published shipment event");

        }



    }




}
