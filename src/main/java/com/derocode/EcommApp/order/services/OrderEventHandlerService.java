package com.derocode.EcommApp.order.services;

import com.derocode.EcommApp.enums.SharedOrderStatus;
import com.derocode.EcommApp.events.SharedShipmentEventDto;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.order.models.Order;
import com.derocode.EcommApp.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventHandlerService {

    private final OrderRepository orderRepository;

    @Transactional
    public void handleShipmentEvent(@NonNull SharedShipmentEventDto event) {
        if(Objects.equals(event.status(),"SHIPPED")){
            Order order = orderRepository.findById(event.orderId()).orElseThrow(() ->
                    new SharedResourceNotFoundException("Order not found for this shipment"));
            order.setStatus(SharedOrderStatus.COMPLETE);
            order.setUpdatedAt(LocalDateTime.now());

            // Save method not needed since using @Transactional
            // Hibernate will issue the update when the transaction commits
            // orderRepository.save(order);
        }
    }
}
