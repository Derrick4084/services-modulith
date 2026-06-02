package com.derocode.EcommApp.order.internals;

import java.math.BigDecimal;

public record OrderLineDto(
        Long productId,
        String productName,
        BigDecimal price,
        double quantity,
        BigDecimal lineTotal
) {
}
