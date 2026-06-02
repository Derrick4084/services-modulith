package com.derocode.EcommApp.product.internals;

//import com.derocode.EcommApp.order.OrderDto;
//import com.derocode.EcommApp.order.OrderEvent;
//import com.derocode.EcommApp.order.OrderFacade;
//import com.derocode.EcommApp.order.internals.OrderLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

import java.util.Objects;

@RequiredArgsConstructor
public class EventsHandler {

//    private final OrderFacade orderFacade;
    private final ProductRepository repository;


//    @EventListener
//    public void handleOrderListener(OrderEvent event) {
//
//        if(Objects.equals(event.status(),"DENIED")) {
//
//            OrderDto orderDto = orderFacade.getOrderById(event.orderId());
//
//            for(OrderLineDto dto : orderDto.orderLines()) {
//
//                Product product = repository.getProductById(dto.productId()).orElseThrow(()->
//                        new RuntimeException("Product not found"));
//
//                product.setAvailableQuantity(product.getAvailableQuantity() + dto.quantity());
//
//                repository.save(product);
//
//
//
//            }
//
//
//
//
//        }
//
//
//    }
}
