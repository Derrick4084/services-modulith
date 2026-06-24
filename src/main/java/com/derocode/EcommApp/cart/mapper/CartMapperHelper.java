package com.derocode.EcommApp.cart.mapper;

import com.derocode.EcommApp.product.ProductFacade;
import com.derocode.EcommApp.product.ProductResponseDto;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class CartMapperHelper {

    private final ProductFacade productFacade;

    public CartMapperHelper(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @Named("getProductName")
    String getProductName(Long id){
        ProductResponseDto responseDto = productFacade.getProductById(id);
        return responseDto.name();
    }
}
