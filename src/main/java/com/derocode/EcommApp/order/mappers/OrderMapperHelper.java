package com.derocode.EcommApp.order.mappers;

import com.derocode.EcommApp.enums.SharedOrderStatus;
import com.derocode.EcommApp.enums.SharedPaymentMethod;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public class OrderMapperHelper {

    // Enum ↔ String
    public SharedPaymentMethod map(String value) {
        return SharedPaymentMethod.valueOf(value);
    }
    public String map(@NonNull SharedPaymentMethod value) {
        return value.name();
    }

    @Named("orderStatusToString")
    public String mapOrderStatusToString(@NonNull SharedOrderStatus value) {
        return value.name();
    }

    @Named("toOrderStatus")
    public SharedOrderStatus mapToOrderStatus(String value) {
        return SharedOrderStatus.valueOf(value);
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
}



