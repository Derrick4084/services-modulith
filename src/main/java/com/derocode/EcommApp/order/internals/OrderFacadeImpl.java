package com.derocode.EcommApp.order.internals;

import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;

    @Override
    public OrderResponseDto createOrder(@NonNull CreateOrderDto request) {
        return orderService.createOrder(request);
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        return orderService.getOrderById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return orderService.existsById(id);
    }

}
