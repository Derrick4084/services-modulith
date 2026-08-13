package com.derocode.EcommApp.order;

import com.derocode.EcommApp.customer.CustomerFacade;
import com.derocode.EcommApp.order.mappers.OrderMapperImpl;
import com.derocode.EcommApp.order.services.OrderService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerFacade customerFacade;
    private final OrderMapperImpl orderMapper;

    @PostMapping("/order/create")
    public ResponseEntity<Object> createOrder(@RequestBody @NonNull CreateOrderDto request){
        if(!customerFacade.existsByEmail(request.customerEmail())) {
            return ResponseEntity.badRequest().body("No customer found to create this order");
        }
        OrderResponseDto response = orderMapper.entityToResponse(orderService.createOrder(request));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/id/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id){
        OrderResponseDto response = orderMapper.entityToResponse(orderService.getOrderById(id));
        return ResponseEntity.ok(response);

    }

    @GetMapping("/order/reference/{reference}")
    public ResponseEntity<OrderResponseDto> getOrderByReference(@PathVariable String reference){
        OrderResponseDto response = orderMapper.entityToResponse(orderService.getOrderByReference(reference));
        return ResponseEntity.ok(response);

    }
}
