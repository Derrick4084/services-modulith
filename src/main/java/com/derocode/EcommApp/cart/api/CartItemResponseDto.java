package com.derocode.EcommApp.cart.api;

import java.math.BigDecimal;

public record CartItemResponseDto(
        String name,
        Double quantity,
        BigDecimal total
) {
}
