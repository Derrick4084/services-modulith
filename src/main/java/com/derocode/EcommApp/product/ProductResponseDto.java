package com.derocode.EcommApp.product;


import java.math.BigDecimal;

public record ProductResponseDto(

       Long id,

       String name,

       String description,

       Double availableQuantity,

       BigDecimal price,

       String category
) {
}
