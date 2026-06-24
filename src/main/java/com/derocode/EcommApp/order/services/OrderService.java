package com.derocode.EcommApp.order.services;

import com.derocode.EcommApp.enums.SharedOrderStatus;
import com.derocode.EcommApp.enums.SharedPaymentMethod;
import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.components.OrderUtils;
import com.derocode.EcommApp.order.mappers.OrderMapperImpl;
import com.derocode.EcommApp.order.models.Order;
import com.derocode.EcommApp.order.models.OrderLine;
import com.derocode.EcommApp.order.repositories.OrderRepository;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import com.derocode.EcommApp.product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderUtils utils;
    private final ProductFacade productFacade;
    private final ApplicationEventPublisher publisher;
    private final OrderMapperImpl mapper;


    @Transactional
    public Order createOrder(@NonNull CreateOrderDto request) {

        List<ProductOrderRequestDto> requestedProducts = request.products()
                .stream().map(ophr ->
                        new ProductOrderRequestDto(
                                ophr.productId(),
                                ophr.quantity()
                        )).toList();

        Order order = Order.builder().build();
        order.setCustomerEmail(request.customerEmail());
        order.setReference(utils.generateRef());
        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(SharedPaymentMethod.valueOf(request.paymentMethod()));
        order.setStatus(SharedOrderStatus.CREATED);

        Order partialOrder = orderRepository.save(order);

        List<ProductResponseDto> availableProducts = productFacade.getAvailableProducts(requestedProducts, partialOrder.getId());
        List<OrderLine> orderLines = utils.getOrderLines(availableProducts, partialOrder);
        double totalAmount = utils.productsTotalAmount(orderLines);

        partialOrder.setOrderLines(orderLines);
        partialOrder.setTotalAmount(BigDecimal.valueOf(totalAmount));

        try {
            Order completedOrder = orderRepository.save(partialOrder);
            SharedOrderEventDto orderEventDto = mapper.orderToEventDto(completedOrder);
            publisher.publishEvent(orderEventDto);
            System.out.println("Published order event");
            return completedOrder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Order getOrderById(Long id) {
//        Optional<Order> order = orderRepository.findByIdWithOrderLines(id);

        return orderRepository.findByIdWithOrderLines(id).orElseThrow(()->
                new SharedResourceNotFoundException("Order with id " + id.toString() + " not found")
        );





//        return order.map(mapper::entityToResponse).orElseThrow(()-> new SharedResourceNotFoundException(
//                "Order not found"
//        ));
    }


    public Boolean existsById(Long id) {
        return orderRepository.findById(id).isPresent();
    }
}







