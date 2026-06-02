package com.derocode.EcommApp.product;

import java.math.BigDecimal;

public record AddProductRequestDto(
        String productName,
        String description,
        Double quantity,
        BigDecimal price,
        String category
) {
}
