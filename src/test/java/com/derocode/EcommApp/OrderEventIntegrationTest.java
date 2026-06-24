package com.derocode.EcommApp;


import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.CreateOrderItemDto;
import com.derocode.EcommApp.order.mappers.OrderMapperImpl;
import com.derocode.EcommApp.order.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Autowired
    OrderMapperImpl orderMapper;

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


        OrderResponseDto response = orderMapper.entityToResponse(orderService.createOrder(dto));

        assertThat(events.stream(SharedOrderEventDto.class).count()).isEqualTo(1);



    }
}
