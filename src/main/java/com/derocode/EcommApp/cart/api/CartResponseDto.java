package com.derocode.EcommApp.cart.api;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDto(
        String customerEmail,
        BigDecimal totalAmount,
        List<CartItemResponseDto> items
) {
}
