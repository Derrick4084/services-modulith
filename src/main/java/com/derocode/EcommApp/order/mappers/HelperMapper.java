package com.derocode.EcommApp.order.mappers;

import com.derocode.EcommApp.enums.OrderStatus;
import com.derocode.EcommApp.enums.PaymentMethod;
import com.derocode.EcommApp.order.api.OrderLineDto;
import com.derocode.EcommApp.order.models.OrderLine;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public class HelperMapper {

    // Enum ↔ String
    public PaymentMethod map(String value) {
        return PaymentMethod.valueOf(value);
    }
    public String map(@NonNull PaymentMethod value) {
        return value.name();
    }

    @Named("orderStatusToString")
    public String mapOrderStatusToString(@NonNull OrderStatus value) {
        return value.name();
    }

    @Named("toOrderStatus")
    public OrderStatus mapToOrderStatus(String value) {
        return OrderStatus.valueOf(value);
    }

    // BigDecimal ↔ Double
    public Double map(@NonNull BigDecimal value) {
        return value.doubleValue();
    }
    public BigDecimal map(Double value) {
        return BigDecimal.valueOf(value);
    }


    public Integer map(Long value) {
        if (value == null) return null;
        if (value > Integer.MAX_VALUE || value < Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Value out of int range: " + value);
        }
        return value.intValue();
    }

//    public OrderProductResponse map(@NonNull OrderLine line) {
//        return OrderProductResponse.newBuilder()
//                .setName(line.getProductName())
//                .setPrice(line.getPrice().doubleValue())
//                .setQuantity(line.getQuantity())
//                .build();
//    }

//    public CreateOrderProductResponse mapToCreate(@NonNull OrderLine line) {
//        return CreateOrderProductResponse.newBuilder()
//                .setName(line.getProductName())
//                .setPrice(line.getPrice().doubleValue())
//                .setQuantity(line.getQuantity())
//                .build();
//    }

    public OrderLineDto mapToDTO(@NonNull OrderLine line) {
        return new OrderLineDto(
                line.getProductId(),
                line.getProductName(),
                line.getPrice(),
                line.getQuantity(),
                line.getLineTotal()
        );
    }

    // Time conversion
//    public Timestamp map(@NonNull LocalDateTime value) {
//        Instant instant = value.toInstant(ZoneOffset.UTC);

//        return Timestamp.newBuilder()
//                .setSeconds(instant.getEpochSecond())
//                .setNanos(instant.getNano())
//                .build();
    }

//    public LocalDateTime map(@NonNull Timestamp value) {
//        Instant instant = Instant.ofEpochSecond(value.getSeconds(), value.getNanos());
//        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
//    }

