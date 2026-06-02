package com.derocode.EcommApp.product.internals;

import com.derocode.EcommApp.product.*;
import com.derocode.EcommApp.product.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ProductFacadeImpl implements ProductFacade{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    @Override
    public ProductResponseDto getProductByName(@NonNull String name) {
        Optional<Product> product = productRepository.getProductByName(name);
        if (product.isPresent()) {
            return mapper.entityToResponse(product.get());
        }
        else {
            throw new RuntimeException("Product not found");
        }
    }

    @Override
    public ProductResponseDto getProductById(@NonNull Long id) {
        Product product = productRepository.getProductById(id).orElseThrow(
                ()-> new RuntimeException("Product does not exists")
        );
        return mapper.entityToResponse(product);
    }

    @Override
    public ProductResponseDto addNewProduct(@NonNull AddProductRequestDto addProductRequest) {

        Category category = categoryRepository.findByName(addProductRequest.category()).orElseThrow(()->
                new RuntimeException("There is no category with that name")
        );


        if (productRepository.getProductByName(addProductRequest.productName()).isEmpty()) {
            Product product = mapper.addProductRequestToEntity(addProductRequest);
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);
            return mapper.entityToResponse(savedProduct);
        }
        else {
            throw  new RuntimeException("Product with that name already exists");
        }
    }

    @Override
    public List<ProductResponseDto> getAvailableProducts(@NonNull List<ProductOrderRequestDto> requestedProducts) {
        List<ProductResponseDto> availableProducts = new ArrayList<>();
        for (ProductOrderRequestDto productRequest : requestedProducts) {
            Product retProduct = productRepository.getProductById(productRequest.productId())
                    .orElseThrow(()-> new RuntimeException("Product not found"));
            if(retProduct.getAvailableQuantity() >= productRequest.quantity() && retProduct.getAvailableQuantity() != 0) {
                double var = retProduct.getAvailableQuantity() - productRequest.quantity();
                availableProducts.add(
                        new ProductResponseDto(
                                retProduct.getId(),
                                retProduct.getName(),
                                retProduct.getDescription(),
                                productRequest.quantity(),
                                retProduct.getPrice(),
                                retProduct.getCategory().getName()));
                retProduct.setAvailableQuantity(var);
                productRepository.save(retProduct);
            }
        }
        return availableProducts;
    }
}
