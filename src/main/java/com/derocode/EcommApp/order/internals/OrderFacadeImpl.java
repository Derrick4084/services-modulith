package com.derocode.EcommApp.order.internals;

import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.customer.CustomerResponseDto;
import com.derocode.EcommApp.enums.OrderStatus;
import com.derocode.EcommApp.enums.PaymentMethod;
import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.order.CreateOrderDto;
import com.derocode.EcommApp.order.OrderFacade;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderUtils utils;
    private final CustomerFacade customerFacade;
    private final ProductFacade productFacade;
    private final ApplicationEventPublisher publisher;
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;


    @Override
    public OrderResponseDto createOrder(@NonNull CreateOrderDto request) {

        CustomerResponseDto customerResponseDto = customerFacade.getCustomerByEmail(request.customerEmail());

        List<ProductOrderRequestDto> requestedProducts = request.products()
                .stream().map(ophr ->
                        new ProductOrderRequestDto(
                                ophr.productId(),
                                ophr.quantity()
                        )).toList();

        List<ProductResponseDto> availableProducts = productFacade.getAvailableProducts(requestedProducts);

        Order order = Order.builder().customerEmail(customerResponseDto.email()).build();

        List<OrderLine> orderLines = utils.getOrderLines(availableProducts, order);

        double totalAmount = utils.productsTotalAmount(orderLines);

        order.setReference(utils.generateRef());

        order.setOrderDate(LocalDateTime.now());
        order.setPaymentMethod(PaymentMethod.valueOf(request.paymentMethod()));
        order.setOrderLines(orderLines);
        order.setTotalAmount(BigDecimal.valueOf(totalAmount));
        order.setStatus(OrderStatus.CREATED);
        Order savedOrder = orderRepository.save(order);


        OrderEventDto orderEventDto = mapper.orderToOrderEvent(savedOrder, customerResponseDto);

        publisher.publishEvent(orderEventDto);
        System.out.println("Published order event");

        return mapper.entityToOrderResponseDto(savedOrder);

    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        Optional<Order> order = orderRepository.findByIdWithOrderLines(id);
        return order.map(mapper::entityToOrderResponseDto).orElseThrow(()-> new RuntimeException(
                "Order not found"
        ));


    }
}
