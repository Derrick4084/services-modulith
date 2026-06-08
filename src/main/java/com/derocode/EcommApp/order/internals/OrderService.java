package com.derocode.EcommApp.order.internals;

import com.derocode.EcommApp.enums.OrderStatus;
import com.derocode.EcommApp.enums.PaymentMethod;
import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.events.ShipmentEventDto;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderResponseDto;
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
import java.util.Objects;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderUtils utils;
    private final ProductFacade productFacade;
    private final ApplicationEventPublisher publisher;
    private final OrderMapper mapper;


    @Transactional
    public OrderResponseDto createOrder(@NonNull CreateOrderDto request) {

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
        order.setPaymentMethod(PaymentMethod.valueOf(request.paymentMethod()));
        order.setStatus(OrderStatus.CREATED);


        log.info("before first saved order");

        Order partialOrder = orderRepository.save(order);

        log.info("After first saved order {}", partialOrder);


        List<ProductResponseDto> availableProducts = productFacade.getAvailableProducts(requestedProducts, partialOrder.getId());
        List<OrderLine> orderLines = utils.getOrderLines(availableProducts, partialOrder);
        double totalAmount = utils.productsTotalAmount(orderLines);


        log.info("After getting available products");


        partialOrder.setOrderLines(orderLines);
        partialOrder.setTotalAmount(BigDecimal.valueOf(totalAmount));


        log.info("After partial or set orderLines");



        try {

            Order completedOrder = orderRepository.save(partialOrder);
            log.info("After complete order save");

            OrderEventDto orderEventDto = mapper.orderToOrderEvent(completedOrder);

            publisher.publishEvent(orderEventDto);
            System.out.println("Published order event");

            return mapper.entityToOrderResponseDto(completedOrder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }





    }


    public OrderResponseDto getOrderById(Long id) {
        Optional<Order> order = orderRepository.findByIdWithOrderLines(id);
        return order.map(mapper::entityToOrderResponseDto).orElseThrow(()-> new RuntimeException(
                "Order not found"
        ));
    }

    public Boolean existsById(Long id) {
        return orderRepository.findById(id).isPresent();
    }


    @Transactional
    public void handleShipmentEvent(@NonNull ShipmentEventDto event) {
        if(Objects.equals(event.status(),"SHIPPED")){
            Order order = orderRepository.findById(event.orderId()).orElseThrow(() ->
                    new RuntimeException("Order not found for this shipment"));
            order.setStatus(OrderStatus.COMPLETE);
            order.setUpdatedAt(LocalDateTime.now());

            // Save method not needed since using @Transactional
            // Hibernate will issue the update when the transaction commits
            // orderRepository.save(order);
        }
    }
}







