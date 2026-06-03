package com.derocode.EcommApp.order.internals;

import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.enums.OrderStatus;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.events.ShipmentEventDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl
{

    private final OrderUtils utils;
    private final CustomerFacade customerFacade;
    private final ProductFacade productFacade;
    private final ApplicationEventPublisher events;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;


    @EventListener
    public void handleShipmentEvent(@NonNull ShipmentEventDto event) {
        if(Objects.equals(event.status(),"SHIPPED")){
            Order order = orderRepository.findById(event.orderId()).orElseThrow(() ->
                    new RuntimeException("Order not found for this shipment"));
            order.setStatus(OrderStatus.COMPLETE);
            order.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(order);
        }
    }
}







