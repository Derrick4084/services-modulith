package com.derocode.EcommApp.order;

public interface OrderFacade {

    OrderResponseDto createOrder(CreateOrderDto createOrderDto);

    OrderResponseDto getOrderById(Long id);

    Boolean existsById(Long id);
}
