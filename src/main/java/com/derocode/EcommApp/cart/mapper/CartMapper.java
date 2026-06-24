package com.derocode.EcommApp.cart.mapper;


import com.derocode.EcommApp.cart.api.CartItemResponseDto;
import com.derocode.EcommApp.cart.api.CartResponseDto;
import com.derocode.EcommApp.cart.models.Cart;
import com.derocode.EcommApp.cart.models.CartItem;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        uses = CartMapperHelper.class,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface CartMapper {

    @Mapping(target = "name", source = "productId", qualifiedByName = "getProductName")
    @Mapping(target = "total", source = "totalPrice")
    CartItemResponseDto cartItemToDto(CartItem cartItem);


    @Mapping(target = "items", source = "items")
    CartResponseDto cartToDto(Cart cart);
}

