package com.derocode.EcommApp.product.internals;

import com.derocode.EcommApp.product.*;
import com.derocode.EcommApp.product.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class ProductFacadeImpl implements ProductFacade{

    private final ProductService productService;

    @Override
    public ProductResponseDto getProductByName(@NonNull String name) {
        return productService.getProductByName(name);
    }

    @Override
    public ProductResponseDto getProductById(@NonNull Long id) {
        return productService.getProductById(id);
    }

    @Override
    public ProductResponseDto addNewProduct(@NonNull AddProductRequestDto addProductRequest) {
        return productService.addNewProduct(addProductRequest);
    }

    @Override
    public List<ProductResponseDto> getAvailableProducts(@NonNull List<ProductOrderRequestDto> requestedProducts, Long orderId) {
        return productService.getAvailableProducts(requestedProducts, orderId);
    }

}
