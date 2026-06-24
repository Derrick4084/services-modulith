package com.derocode.EcommApp.order.components;

import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.mappers.OrderMapperImpl;
import com.derocode.EcommApp.order.services.OrderService;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final OrderMapperImpl orderMapper;

    @Override
    public OrderResponseDto createOrder(@NonNull CreateOrderDto request) {
        return orderMapper.entityToResponse(orderService.createOrder(request));
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        return orderMapper.entityToResponse(orderService.getOrderById(id));
    }

    @Override
    public Boolean existsById(Long id) {
        return orderService.existsById(id);
    }

}
