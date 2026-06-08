package com.derocode.EcommApp.product;

import java.util.List;

public interface ProductFacade {

    ProductResponseDto getProductByName(String name);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto addNewProduct(AddProductRequestDto addProductRequest);

    List<ProductResponseDto> getAvailableProducts(List<ProductOrderRequestDto> products, Long orderId);
}

