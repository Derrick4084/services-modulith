package com.derocode.EcommApp.product.mappers;


import com.derocode.EcommApp.product.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.models.Category;
import com.derocode.EcommApp.product.models.Product;
import org.jspecify.annotations.NonNull;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface ProductMapper {

    default ProductResponseDto entityToResponse(@NonNull Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getName()
        );
    }

    default Product addProductRequestToEntity(@NonNull AddProductRequestDto request) {

        return Product.builder()
                .name(request.productName())
                .description(request.description())
                .availableQuantity(request.quantity())
                .price(request.price())
                .category(Category.builder().name(request.category()).build())
                .build();
    }

}

