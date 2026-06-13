package com.derocode.EcommApp;


import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.api.CreateOrderItemDto;
import com.derocode.EcommApp.order.services.OrderService;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.modulith.test.PublishedEvents;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@RecordApplicationEvents
public class OrderEventIntegrationTest {

    @Autowired
    OrderService orderService;

    @Autowired
    ApplicationEvents events;

    @Test
    void publishesOrderCreatedEvent() {

        List<CreateOrderItemDto> products = new ArrayList<>();

        CreateOrderItemDto item1 = new CreateOrderItemDto(
                1L,
                1.0
        );

        CreateOrderItemDto item2 = new CreateOrderItemDto(
                2L,
                2.0
        );

        products.add(item1);
        products.add(item2);

        CreateOrderDto dto = new CreateOrderDto(
                "DISCOVER_CARD",
                "jdoe@gmail.com",
                products
        );


        OrderResponseDto response = orderService.createOrder(dto);

        assertThat(events.stream(OrderEventDto.class).count()).isEqualTo(1);



    }
}
