package com.derocode.EcommApp.product;

import com.derocode.EcommApp.product.api.AddProductRequestDto;

import java.util.List;

public interface ProductFacade {

    ProductResponseDto getProductByName(String name);

    ProductResponseDto getProductById(Long id);

    ProductResponseDto addNewProduct(AddProductRequestDto addProductRequest);

    List<ProductResponseDto> getAvailableProducts(List<ProductOrderRequestDto> products, Long orderId);
}

