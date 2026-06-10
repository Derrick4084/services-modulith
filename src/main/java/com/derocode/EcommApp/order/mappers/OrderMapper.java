package com.derocode.EcommApp.order.mappers;



import com.derocode.EcommApp.events.OrderEventDto;
import com.derocode.EcommApp.order.api.OrderLineDto;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.models.Order;
import org.jspecify.annotations.NonNull;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(uses = HelperMapper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderMapper {

    default OrderResponseDto entityToOrderResponseDto(@NonNull Order order) {
        List<OrderLineDto> orderItems = order.getOrderLines().stream().map(ol ->
                new OrderLineDto(
                        ol.getProductId(),
                        ol.getProductName(),
                        ol.getPrice(),
                        ol.getQuantity(),
                        ol.getLineTotal()
                )).toList();
        return new OrderResponseDto(
                order.getReference(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getPaymentMethod().name(),
                order.getOrderDate(),
                order.getCustomerEmail(),
                orderItems
        );

    }


    default OrderEventDto orderToOrderEvent(@NonNull Order order) {
        List<OrderLineDto> orderItems = order.getOrderLines().stream().map(ol->
                new OrderLineDto(
                        ol.getProductId(),
                        ol.getProductName(),
                        ol.getPrice(),
                        ol.getQuantity(),
                        ol.getLineTotal()
                )).toList();
        return new OrderEventDto(
                order.getId(),
                order.getReference(),
                order.getStatus().name(),
                order.getTotalAmount(),
                order.getPaymentMethod().name(),
                order.getOrderDate(),
                order.getCustomerEmail()

        );
    }


}
