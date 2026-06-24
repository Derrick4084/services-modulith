package com.derocode.EcommApp.product.services;

import com.derocode.EcommApp.exceptions.SharedResourceExistsException;
import com.derocode.EcommApp.exceptions.SharedResourceNotFoundException;
import com.derocode.EcommApp.product.api.AddProductRequestDto;
import com.derocode.EcommApp.product.ProductOrderRequestDto;
import com.derocode.EcommApp.product.ProductResponseDto;
import com.derocode.EcommApp.product.mappers.ProductMapper;
import com.derocode.EcommApp.product.models.Category;
import com.derocode.EcommApp.product.models.InventoryReservation;
import com.derocode.EcommApp.product.models.Product;
import com.derocode.EcommApp.product.repositories.CategoryRepository;
import com.derocode.EcommApp.product.repositories.InventoryReservationRepository;
import com.derocode.EcommApp.product.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;
    private final InventoryReservationRepository inventoryRepository;

    public Page<ProductResponseDto> getAll(int page, int size) {
        if(size > 20){
            size = 20;
        }
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(pageable).map(mapper::entityToResponse);
    }

    public Product getProductByName(@NonNull String name) {
        Optional<Product> product = productRepository.getProductByName(name);
        if (product.isPresent()) {
            return product.get();
        }
        else {
            throw new SharedResourceNotFoundException("Product not found");
        }
    }

    public Product getProductById(@NonNull Long id) {
        return productRepository.getProductById(id).orElseThrow(
                ()-> new SharedResourceNotFoundException("Product does not exists")
        );
    }

    public Product addNewProduct(@NonNull AddProductRequestDto addProductRequest) {
        Category category = categoryRepository.findByName(addProductRequest.category()).orElseThrow(()->
                new SharedResourceNotFoundException("There is no category with that name")
        );
        if (productRepository.getProductByName(addProductRequest.productName()).isEmpty()) {
            Product product = mapper.requestToEntity(addProductRequest);
            product.setCategory(category);
            return productRepository.save(product);
        }
        else {
            throw new SharedResourceExistsException("Product with that name already exists");
        }
    }

    public List<ProductResponseDto> getAvailableProducts(@NonNull List<ProductOrderRequestDto> requestedProducts, Long orderId) {
        List<ProductResponseDto> availableProducts = new ArrayList<>();
        for (ProductOrderRequestDto productRequest : requestedProducts) {
            Product retProduct = productRepository.getProductById(productRequest.productId())
                    .orElseThrow(()-> new SharedResourceNotFoundException("Product not found"));
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

                InventoryReservation reservation = InventoryReservation.builder()
                                .orderId(orderId)
                                        .productId(retProduct.getId())
                                                .quantity(productRequest.quantity())
                                                        .build();
                inventoryRepository.save(reservation);
            }
        }
        return availableProducts;
    }
}

