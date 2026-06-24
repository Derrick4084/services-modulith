package com.derocode.EcommApp.product.services;

import com.derocode.EcommApp.product.*;
import com.derocode.EcommApp.product.api.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import com.derocode.EcommApp.product.mappers.ProductMapperImpl;
import com.derocode.EcommApp.product.models.Product;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class ProductFacadeImpl implements ProductFacade{

    private final ProductService productService;
    private final ProductMapperImpl productMapper;

    @Override
    public ProductResponseDto getProductByName(@NonNull String name) {
        Product product = productService.getProductByName(name);
        return productMapper.entityToResponse(product);
    }

    @Override
    public ProductResponseDto getProductById(@NonNull Long id) {
        Product product = productService.getProductById(id);
        return productMapper.entityToResponse(product);
    }

    @Override
    public ProductResponseDto addNewProduct(@NonNull AddProductRequestDto addProductRequest) {
        Product product = productService.addNewProduct(addProductRequest);
        return productMapper.entityToResponse(product);
    }

    @Override
    public List<ProductResponseDto> getAvailableProducts(@NonNull List<ProductOrderRequestDto> requestedProducts, Long orderId) {
        return productService.getAvailableProducts(requestedProducts, orderId);
    }

}
