package com.derocode.EcommApp.order.mappers;



import com.derocode.EcommApp.events.SharedOrderEventDto;
import com.derocode.EcommApp.order.api.OrderLineDto;
import com.derocode.EcommApp.order.OrderResponseDto;
import com.derocode.EcommApp.order.models.Order;
import com.derocode.EcommApp.order.models.OrderLine;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(uses = OrderMapperHelper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface OrderMapper {


    @Mapping(target = "products", source = "orderLines")
    OrderResponseDto entityToResponse(Order order);


    OrderLineDto orderLineToDto(OrderLine orderline);


    @Mapping(target = "orderId", source = "id")
    SharedOrderEventDto orderToEventDto(Order order);

}
