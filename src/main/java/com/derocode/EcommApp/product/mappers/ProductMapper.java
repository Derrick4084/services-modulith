package com.derocode.EcommApp.product.mappers;


import com.derocode.EcommApp.product.api.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.models.Product;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ProductMapper {

    @Mapping(target = "category", expression = "java(product.getCategory().getName())")
    ProductResponseDto entityToResponse(Product product);


    @Mapping(target = "name", source = "productName")
    @Mapping(target = "category", expression = "java(com.derocode.EcommApp.product.models.Category.builder().name(request.category()).build())")
    Product requestToEntity(AddProductRequestDto request);


}

